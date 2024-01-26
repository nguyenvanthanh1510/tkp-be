package com.dev.tkpbe.configs.exceptions;

public class TkpCommonException extends RuntimeException {
  public TkpCommonException(String msg) {
    super(msg);
  }

  public TkpCommonException(Exception e) {
    super(e);
  }

  public TkpCommonException(String msg, Exception e) {
    super(msg, e);
  }
}
