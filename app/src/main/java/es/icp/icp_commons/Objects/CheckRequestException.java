package es.icp.icp_commons.Objects;

public class CheckRequestException extends Exception {

    private String function;

    public CheckRequestException(String message) {
        super(message);
    }

    public CheckRequestException(String message, String function) {
        super(message);
        this.function = function;
    }

    public CheckRequestException(String message, Throwable cause, String function) {
        super(message, cause);
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "CheckRequestException{" +
                "function='" + function + '\'' +
                '}';
    }
}
