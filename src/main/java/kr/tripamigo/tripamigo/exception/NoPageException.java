package kr.tripamigo.tripamigo.exception;

public class NoPageException extends RuntimeException {

    private final String url;

    public NoPageException(String message, String url) {
        super(message);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
