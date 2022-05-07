package com.arme.demo01;

public class Greeting {

    private Long Id;
    private String message;

    public Greeting(Long id, String message) {
        Id = id;
        this.message = message;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
