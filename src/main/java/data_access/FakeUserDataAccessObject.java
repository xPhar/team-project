package data_access;

import entity.Assignment;
import entity.Course;
import entity.User;
import usecase.Submit.SubmitUserDataAccessInterface;

import java.io.File;
import java.io.IOException;

public class FakeUserDataAccessObject extends FileToStringDataAccessObject implements SubmitUserDataAccessInterface {

    @Override
    public void submit(File studentFile, User student, Course course, Assignment assignment) throws IOException {
        String encryptedFileString = readFileToString(studentFile);
        // TODO: connect to gradeAPI, raise IO exception if fail to connect
        // TODO: encapsulate encryptedFileString into a json,
        //  probably with metadata about student, assignment, and course
        // TODO: send the json to gradeAPI
        // example json file see https://github.com/xPhar/team-project/blob/gradeApiDAO/src/main/java/data_access/GradeAPIDataAccessObject.java
        // Demo use only, will be implemented on GradeAPIDAO
    }
}
