package usecase.Submit;

import app.Session;

import java.io.File;
import java.io.IOException;

public interface SubmitUserDataAccessInterface {
    /*
    Send this file to Database, signature may change
     */
    void submit(File studentFile, Session session) throws IOException;
}
