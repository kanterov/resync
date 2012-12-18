package ru.nsu.ccfit.resync.storage.github.ui;

public class LoginParametersStorage {

    private String login;
    private String password;
    private boolean shouldRemember;

    public LoginParametersStorage() {
    }

    public LoginParametersStorage(String login, String password, boolean shouldRemember) {
        this.login = login;
        this.password = password;
        this.shouldRemember = shouldRemember;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isShouldRemember() {
        return shouldRemember;
    }

    public void setShouldRemember(boolean shouldRemember) {
        this.shouldRemember = shouldRemember;
    }

}
