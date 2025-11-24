package data_access;

import entity.Assignment;
import entity.Course;
import entity.User;
import usecase.Submit.SubmitUserDataAccessInterface;
import usecase.login.LoginDataAccessInterface;

import java.io.File;
import java.io.IOException;

public class FakeUserDataAccessObject implements SubmitUserDataAccessInterface, LoginDataAccessInterface {

    @Override
    public void submit(File studentFile, User student, Course course, Assignment assignment) throws IOException {
        // Demo use only, will be implemented on GradeAPIDAO
    }

    @Override
    public boolean existsByName(String username) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void setActiveUser(User user) {
        // Does nothing :D
    }
}
