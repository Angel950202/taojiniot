package com.taojin.iot.base.comm.utils;

import java.util.UUID;

public class UUIDTools
{
  public static String getUUID()
  {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
