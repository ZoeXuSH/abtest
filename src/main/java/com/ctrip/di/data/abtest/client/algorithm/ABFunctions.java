package com.ctrip.di.data.abtest.client.algorithm;

import org.apache.commons.lang.StringUtils;
import com.ctrip.di.data.abtest.client.config.ABTestConstant;

public class ABFunctions {

  public static boolean isAppIdValid(String appId, String wirelessAppId) {
    return StringUtils.isBlank(wirelessAppId) || ABTestConstant.DEFAULT_ALL.equals(wirelessAppId)
        || (!StringUtils.isBlank(appId) && appId.equals(wirelessAppId));
  }

  public static boolean isAppVerValid(String appVer, Float startAppVer, Float stopAppVer) {
    if (startAppVer == null && stopAppVer == null) {
      return true;
    }

    if (StringUtils.isBlank(appVer)) {
      return false;
    }

    try {
      Float appVerF = Float.parseFloat(appVer);
      return (startAppVer == null || Float.compare(appVerF, startAppVer) >= 0)
          && (stopAppVer == null || Float.compare(appVerF, stopAppVer) <= 0);
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isSystemCodeValid(String systemCode, String urgentSystemCode) {
    return StringUtils.isBlank(urgentSystemCode)
        || ABTestConstant.DEFAULT_ALL.equals(urgentSystemCode)
        || (!StringUtils.isBlank(systemCode) && systemCode.equals(urgentSystemCode));
  }

}
