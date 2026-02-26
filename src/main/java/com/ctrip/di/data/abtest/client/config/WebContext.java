package com.ctrip.di.data.abtest.client.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web abtest
 */
public class WebContext {

  private static final Pattern AB_ALTERNATIVE =
      Pattern.compile("(?<=ab_alternative=)([a-z])", Pattern.CASE_INSENSITIVE);

  private HttpServletRequest request;

  private HttpServletResponse response;

  public WebContext(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  private Cookie getCookieByName(String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(cookieName)) {
          return cookie;
        }
      }
    }
    return null;
  }

  private Object getAttributeByName(String attributeName) {
    return request.getAttribute(attributeName);
  }

  private void setUid(String uid) {
    Cookie cookie =
        createABTestCookie(ABTestConstant.ABTEST_UID, uid, ABTestConstant.ABTEST_MAX_AGE);
    if (cookie != null) {
      response.addCookie(cookie);
    }

    request.setAttribute(ABTestConstant.ABTEST_UID, uid);
  }

  private void setExpCookie(String value) {
    Cookie cookie = createABTestCookie(ABTestConstant.ABTEST_GIVEN_VERSION, value,
        ABTestConstant.ABTEST_MAX_AGE);
    if (cookie != null) {
      response.addCookie(cookie);
    }
  }

  public String getUid() {
    Cookie userCookie = getCookieByName(ABTestConstant.ABTEST_UID);
    String uid = null;
    if (userCookie != null) {
      uid = userCookie.getValue();
      // max age of the cookie is 1000 days, HttpServletResponse.addCookie will reset the max age of
      // the cookie, but will add more cookies on client browser, so don't call setUid
      // setUid(uid);
    } else {
      Object uidObj = getAttributeByName(ABTestConstant.ABTEST_UID);
      if (uidObj == null) {
        uid = generateUid();
        setUid(uid);
      } else {
        uid = uidObj.toString();
      }
    }

    return uid;
  }

  public String getVersion(String expCode) {
    if (expCode == null) {
      return null;
    }

    String version = null;
    String queryString = request.getQueryString();
    Cookie cookie = getCookieByName(ABTestConstant.ABTEST_GIVEN_VERSION);
    if (queryString != null && queryString.contains(ABTestConstant.ABTEST_QUERY)) {
      Matcher matcher = AB_ALTERNATIVE.matcher(queryString);
      if (matcher.find())
        version = matcher.group().toUpperCase();
      if (version == null) {
        return null;
      }

      String cookieValue = expCode + ":" + version;
      if (cookie != null) {
        String oldCookieValue = cookie.getValue();
        if (oldCookieValue != null && !oldCookieValue.isEmpty()) {
          oldCookieValue =
              oldCookieValue.replaceAll("(^|,)?" + expCode + ":[A-Z]", "").replaceAll("^,", "");
          cookieValue = oldCookieValue + "," + cookieValue;
        }
      }
      setExpCookie(cookieValue);
    } else {
      if (cookie != null) {
        String cookieValue = cookie.getValue();
        version = parseVersion(cookieValue, expCode);
      }
    }

    return version;
  }

  private String parseVersion(String cookieValue, String expCode) {
    if (cookieValue == null || expCode == null) {
      return null;
    }

    String[] codeVers = cookieValue.split(",");
    for (String codeVerStr : codeVers) {
      String[] codeVer = codeVerStr.split(":");
      if (codeVer != null && codeVer.length > 1) {
        if (expCode.equals(codeVer[0])) {
          return codeVer[1].trim().toUpperCase();
        }
      }
    }

    return null;
  }

  private Cookie createABTestCookie(String cookieName, String cookieValue, int maxAge) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(maxAge);
    cookie.setPath("/");
    cookie.setDomain(request.getServerName());
    return cookie;
  }

  private String generateUid() {
    return UUID.randomUUID().toString();
  }

}
