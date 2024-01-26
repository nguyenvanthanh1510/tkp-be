package com.dev.tkpbe.commons.constants;

public class TkpConstant {

  public static class ERROR {

    public static class AUTH {
      public static final String fAILED = "error.auth.failed";
      public static final String NOT_FOUND = "error.auth.credentials.not.found";
      public static final String CREDENTIALS_INVALID = "error.auth.credentials.invalid";
    }

    public static class REQUEST {
      public static final String INVALID_PATH_VARIABLE = "error.request.path.variable.invalid";
      public static final String INVALID_BODY = "error.request.body.invalid";
      public static final String INVALID_PARAM = "error.request.param.invalid";
      public static final String INVALID = "error.request.invalid";
    }

    public static class SECURITY {
      public static final String GENERATE_KEY_PAIR = "error.security.generate.key.pair";
      public static final String ENCODE_KEY = "error.security.encode.key.pair";
      public static final String DECODE_KEY = "error.security.decode.key.pair";
    }


    public static class USER {
      public static final String NOT_EXIST = "error.user.not.exist";
      public static final String EXIST = "error.user.exist";
      public static final String NOT_ASSIGNED_ROLE = "error.user.not.assigned.role";
    }

    public static class ROLE {
      public static final String NOT_EXIST = "error.role.not.exist";
      public static final String NOT_ALLOWED = "error.role.not.allowed";
      public static final String EXIST = "error.role.exist";
    }

    public static class TIME {
      public static final String NOT_CHECK_IN = "error.time.not.checkin";
      public static final String CHECK_IN = "error.time.exist.checkin";
      public static final String CHECK_OUT = "error.time.exist.checkout";
    }

    public static class SERVER {
      public static final String INTERNAL = "error.server.internal";
    }
  }
}
