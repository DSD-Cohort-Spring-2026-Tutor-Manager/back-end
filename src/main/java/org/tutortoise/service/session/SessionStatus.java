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

    public static SessionStatus session(String value){
        for(SessionStatus sessionStatus : SessionStatus.values()){
            if(sessionStatus.value.equalsIgnoreCase(value)){
                return sessionStatus;
            }
        }
        throw new IllegalArgumentException("Invalid session status: " + value+". Valid values are: scheduled, completed, cancelled, all.");
    }

}
