package life.majiang.community.exception;

public class CustomizeException extends RuntimeException {
    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
