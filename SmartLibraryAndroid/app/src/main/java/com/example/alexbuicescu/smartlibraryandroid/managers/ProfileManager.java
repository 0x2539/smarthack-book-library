package com.example.alexbuicescu.smartlibraryandroid.managers;

/**
 * Created by alexbuicescu on 10/23/16.
 */
public class ProfileManager {
    private static ProfileManager ourInstance = new ProfileManager();

    private String firstName;
    private String lastName;
    private String email;

    public static ProfileManager getInstance() {
        return ourInstance;
    }

    private ProfileManager() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
