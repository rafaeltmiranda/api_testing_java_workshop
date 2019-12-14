package org.workshop.api.testing.exception;

public class ApiTestingException extends RuntimeException {

    private String uuid;

    public ApiTestingException(Throwable e) {
        super(e);
    }

    public ApiTestingException(String m) {
        super(m);
    }

    public ApiTestingException(Throwable e, String uuid) {
        super(e);
        this.uuid = uuid;
    }

    public ApiTestingException(String m, String uuid) {
        super(m);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
