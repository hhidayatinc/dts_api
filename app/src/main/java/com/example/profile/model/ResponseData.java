package com.example.profile.model;

import java.util.List;

public class ResponseData {
    private String value;
    private String message;
    private List<Profile> result;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Profile> getResult() {
        return result;
    }
}
