package data_access;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * API Usage idea:
 *
 * For the purposes of our demo, I think it's best we implement a single class for now.
 * Should we have the time, we can work on creating additional classes, enrolling in a class, etc.
 *
 * That said, we will have one api user which stores all data related to the class.
 * Then, each student and instructor will have their own user in the api database.
 *
 * Here's an example of how a student user's object will look: ("Get User Object From DB" API call response)
 * {
 *   "message": "User retrieved successfully",
 *   "status_code": 200,
 *   "user": {
 *     "_id": {
 *       "$oid": "691947bdbb87ae81c84d378d"
 *     },
 *     "creation_time": null,
 *     "info": {
 *         "type": "student"
 *         "userData": {
 *             "firstName": "first",
 *             "lastName": "last",
 *             "courses": [
 *                  "CSC207"
 *             ]
 *         }
 *     },
 *     "password": "password",
 *     "username": "student"
 * }
 *
 * Here's an example of how a class user's object will look: ("Get User Object From DB" API call response)
 *
 * {
 *   "message": "User retrieved successfully",
 *   "status_code": 200,
 *   "user": {
 *     "_id": {
 *       "$oid": "691947bdbb87ae81c84d378d"
 *     },
 *     "creation_time": null,
 *     "info": {
 *         "type": "course"
 *         "courseData": {
 *             "instructors": [
 *                  "someInstructor"
 *             ],
 *             "students": [
 *                  "student1",
 *                  "student2"
 *             ],
 *             "latePolicy": "No Late Submissions Accepted!!!",
 *             "assignments": {
 *                 "assignmentName1": {
 *                     "creationDate": "HH:MM:SS-DD/MM/YYYY",
 *                     "dueDate": "HH:MM:SS-DD/MM/YYYY",
 *                     "gradePeriod": "2",
 *                     "description": "this is an assignment. do it. or don't, whatever.",
 *                     "supportedFileTypes": ".txt",
 *                     "submissions: {
 *                          "studentName": {
 *                              "submittedAt": "HH:MM:SS-DD/MM/YYYY",
 *                              "fileName": "example.txt",
 *                              "fileData": "Look at this!\r\nA text file? :o",
 *                              "status": "graded",
 *                              "grade": "21",
 *                              "feedback": "do better next time"
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     },
 *     "password": "COURSE_PASSWORD",
 *     "username": "course-CSC207"
 * }
 *
 */

/**
 * The DAO for accessing notes stored in the database.
 *
 * <p>This class demonstrates how your group can use the password-protected user
 * endpoints of the API used in lab 5 to store persistent data in your program.
 * </p>
 *
 * <p>You can also refer to the lab 5 code for signing up a new user and other use cases.
 * </p>
 * See
 * <a href=
 * "https://www.postman.com/cloudy-astronaut-813156/
 * csc207-grade-apis-demo/documentation/fg3zkjm/5-password-protected-user">
 *     the documentation</a>
 * of the API for more details.
 */
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
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                return;
            }
            else if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new DataAccessException("message could not be found or password was incorrect");
            }
            else {
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
        JSONObject info = getUserInfo(username);

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
        requestBody.put("info", infoObject);
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) != SUCCESS_CODE) {
                if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                    throw new DataAccessException("message could not be found or password was incorrect");
                } else {
                    throw new DataAccessException("database error: " + responseBody.getString(MESSAGE));
                }
            }
        }
        catch (IOException | JSONException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public JSONObject getUserInfo(String username) throws DataAccessException {
        final JSONObject userJSONObject = getUserObject(username);
        return userJSONObject.getJSONObject("info");
    }

    public JSONObject getUserObject(String username) throws DataAccessException {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
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
            } else {
                return false;
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
