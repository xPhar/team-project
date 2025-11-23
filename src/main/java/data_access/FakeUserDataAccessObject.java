package data_access;

import entity.Assignment;
import entity.Course;
import entity.User;
import usecase.Submit.SubmitUserDataAccessInterface;

import java.io.File;
import java.io.IOException;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface {

    @Override
    public void submit(File studentFile, User student, Course course, Assignment assignment) throws IOException {
        // Demo use only, will be implemented on GradeAPIDAO
    }
}
