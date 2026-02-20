package org.tutortoise.service.session;

public enum SessionStatus {
    scheduled("scheduled"),
    completed("completed"),
    cancelled("cancelled"),
    all("all");

    private final String value;

    SessionStatus(String value) {
        this.value = value;
    }

}
