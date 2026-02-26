package com.ctrip.di.data.abtest.client.exception;

public enum ErrorCode {

  UNKNOWN(0, "unknown error"),

  BLANK_EXPCODE(100, "expCode is blank"),
  BLANK_DIVISIONID(101, "divisionID is blank"),
  INVALID_EXPCODE(102, "expCode is invalid"),

  NULL_METADATA(200, "abtest metadata is null"),
  NULL_ALTERNATIVE(201, "abtest alternative is null"),
  GET_METADATA_FAILED(202, "failed to get abtest metadata"),
  GET_EXPCODE_FAILED(203, "failed to get abtest expcode by bu"),

  GENERATE_UBT_FAILED(300, "failed to generate abtest ubt data"),
  SEND_UBT_FAILED(301, "failed to send abtest ubt data"),

  NULL_HTTP_REQUEST(400, "http request is null"),
  NULL_HTTP_RESPONSE(401, "http response is null");

  private int code;
  private String message;

  private ErrorCode(int code, String msg) {
    this.code = code;
    this.message = msg;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}

