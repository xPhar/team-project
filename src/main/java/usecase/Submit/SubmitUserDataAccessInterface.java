package usecase.Submit;

import entity.Assignment;
import entity.Course;
import entity.User;

import java.io.File;
import java.io.IOException;

public interface SubmitUserDataAccessInterface {
    /*
    Send this file to Database, signature may change
     */
    void submit(File studentFile, User student, Course course, Assignment assignment) throws IOException;
}
