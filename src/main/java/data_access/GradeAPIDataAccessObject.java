package data_access;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GradeAPIDataAccessObject {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String USER_EXIST_MESSAGE = "User exists";
    private static final String INFO = "info";

    public void createUser(String username, String password) throws DataAccessException {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, username);
        requestBody.put(PASSWORD, password);
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        sendAndVerifyRequest(client, request);
    }

    private static void sendAndVerifyRequest(OkHttpClient client, Request request) {
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            // Check for a non-success code
            if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new DataAccessException("message could not be found or password was incorrect");
            }
            else if (responseBody.getInt(STATUS_CODE_LABEL) != SUCCESS_CODE) {
                throw new DataAccessException("database error: " + responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    // Example method, in this case just adds some testing text to the user
    public void modifyUser(String username, String password) throws DataAccessException {
        final JSONObject info = new JSONObject();

        info.put("testField", "Hello World!");

        modifyUserInfoEndpoint(username, password, info);
    }

    // Second example method, this one updates info non-destructively by first fetching the user data
    public void appendToUser(String username, String password) throws DataAccessException {
        final JSONObject info = getUserInfo(username);

        info.put("newField", "This got appended!");

        modifyUserInfoEndpoint(username, password, info);
    }

    public void modifyUserInfoEndpoint(String username, String password, JSONObject infoObject)
            throws DataAccessException {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // PUT METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, username);
        requestBody.put(PASSWORD, password);
        requestBody.put(INFO, infoObject);
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        sendAndVerifyRequest(client, request);
    }

    public JSONObject getUserInfo(String username) throws DataAccessException {
        final JSONObject userJSONObject = getUserObject(username);
        if (!userJSONObject.has(INFO)) {
            throw new DataAccessException("User data is mangled. This user is either saved incorrectly"
                                        + " or is not affiliated with this project");
        }
        return userJSONObject.getJSONObject(INFO);
    }

    public JSONObject getUserObject(String username) throws DataAccessException {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                return responseBody.getJSONObject("user");
            }
            else {
                throw new DataAccessException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkUserExists(String username) throws DataAccessException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                return responseBody.getString(MESSAGE).equals(USER_EXIST_MESSAGE);
            }
            else {
                return false;
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
