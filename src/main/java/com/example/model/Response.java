package com.example.model;

import java.io.Serializable;

public class Response implements Serializable {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
