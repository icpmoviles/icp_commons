package es.icp.icp_commons.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;

import java.io.PrintStream;
import java.io.PrintWriter;

public class VolleyError extends com.android.volley.VolleyError {
    public VolleyError() {
        super();
    }

    public VolleyError(NetworkResponse response) {
        super(response);
    }

    public VolleyError(String exceptionMessage) {
        super(exceptionMessage);
    }

    public VolleyError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
    }

    public VolleyError(Throwable cause) {
        super(cause);
    }

    @Override
    public long getNetworkTimeMs() {
        return super.getNetworkTimeMs();
    }

    @Nullable
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Nullable
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Nullable
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @NonNull
    @Override
    public synchronized Throwable initCause(@Nullable Throwable cause) {
        return super.initCause(cause);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(@NonNull PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(@NonNull PrintWriter s) {
        super.printStackTrace(s);
    }

    @NonNull
    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @NonNull
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(@NonNull StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
