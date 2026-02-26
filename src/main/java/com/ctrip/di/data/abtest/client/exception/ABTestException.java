package com.ctrip.di.data.abtest.client.exception;

public class ABTestException extends Exception {
  
  private static final long serialVersionUID = 1L;

  private ErrorCode errorCode;

  public ABTestException() {
    super();
  }

  public ABTestException(String message) {
    super(message);
  }

  public ABTestException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ABTestException(ErrorCode errorCode, String message) {
    super(errorCode.getMessage() + ": " + message);
    this.errorCode = errorCode;
  }

  public ABTestException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }

  public ABTestException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode.getMessage() + ": " + message, cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}

