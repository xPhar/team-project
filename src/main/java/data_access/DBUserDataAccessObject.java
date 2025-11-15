package data_access;

import entity.Student;
import entity.Submission;
import usecase.Submit.SubmitUserDataAccessInterface;

import java.io.File;
import java.io.IOException;

public class DBUserDataAccessObject implements SubmitUserDataAccessInterface {
    Student student;
    File studentWork;

    @Override
    public void submit(File StudentFile) throws IOException{
        Submission submission = new Submission();

    }
}
