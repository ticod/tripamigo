package kr.tripamigo.tripamigo.exception;

public class LoginException extends RuntimeException {

    private final String url;

    public LoginException(String message, String url) {
        super(message);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
