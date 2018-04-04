package com.cocacola.besanta.api.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Model that is used for representing a type of error
 */

public class ErrorMessage {
    private int errorType;

    public ErrorMessage() {
    }

    public ErrorMessage(int errorType) {
        this.errorType = errorType;
    }

    public @ErrorType
    int getErrorType() {
        return errorType;
    }

    public void setErrorType(@ErrorType int errorType) {
        this.errorType = errorType;
    }

    @IntDef({ErrorMessage.ErrorType.SERVER_ERROR, ErrorType.NO_INTERNET_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
        int SERVER_ERROR = 0;
        int NO_INTERNET_ERROR = 1;
    }
}
