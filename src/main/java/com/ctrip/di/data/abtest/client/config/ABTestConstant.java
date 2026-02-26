package com.ctrip.di.data.abtest.client.config;

public interface ABTestConstant {
    int DEFAULT_MOD = -1;
    int DEFAULT_URGENT_MOD = -2;
    String DEFAULT_ALL = "-1";
    String DEFAULT_VERSION = "A";
    String EXPCODE_REGEX = "[_\\w]+";
    // web abtest
    String ABTEST_UID = "_abtest_userid";
    String ABTEST_QUERY = "ab_alternative";
    String ABTEST_GIVEN_VERSION = "_abtest_internal_";
    int ABTEST_MAX_AGE = 86400000; // in seconds, 1000 days

}
