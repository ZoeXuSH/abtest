package com.ctrip.di.data.abtest.client.algorithm;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class MD5HashGenerator {

  private static final int MOD_FACTOR = 100;

  public static int hash(String... factors) {
    Hasher hasher = Hashing.md5().newHasher();
    for (String factor : factors) {
      if (factor == null) {
        factor = "";
      }
      hasher.putString(factor, Charsets.UTF_8);
    }
    int hash = hasher.hash().asInt();
    return (int) Math.abs((hash % MOD_FACTOR));
  }

}
