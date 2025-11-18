package data_access;

import entity.User;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

public class DummyDataAccessObject implements LoginDataAccessInterface,
        SignupDataAccessInterface {

    @Override
    public boolean existsByName(String username) {
        return false;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User get(String username) {
        return null;
    }

    @Override
    public void setCurrentUsername(String name) {

    }

    @Override
    public String getCurrentUsername() {
        return "";
    }
}