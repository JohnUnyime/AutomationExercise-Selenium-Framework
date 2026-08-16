package com.qa.automationexercise.testdata;

public class InvalidLogin {
    private String email;
    private String password;
    private String scenario;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    // Some rows need a runtime-generated email (e.g. "unregistered email"
    // scenarios) rather than a fixed one written in the JSON, so a stale
    // hardcoded address never accidentally becomes a real registered
    // account later. Any row whose "email" is this marker gets a unique
    // email built on the fly instead of using the literal JSON value.
    public String getResolvedEmail() {
        if ("GENERATE_UNIQUE".equals(email)) {
            return "doesnotexist_" + System.currentTimeMillis() + "@test.com";
        }
        return email;
    }
}
