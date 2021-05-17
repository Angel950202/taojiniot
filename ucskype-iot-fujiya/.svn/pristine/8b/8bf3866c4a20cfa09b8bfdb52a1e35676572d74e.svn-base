package com.taojin.iot.transmit.lib.msghandle;

import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.lib.MessageHandle;

public abstract class StringMessageHandle
  extends MessageHandle
{
  public boolean send(String sessionId, CommunicatType communicatType, String msg)
  {
    boolean result = false;
    switch (communicatType.ordinal())
    {
    case 1: 
      result = super.sockeSend(sessionId, msg.getBytes());
      break;
    case 2: 
      result = super.webSockeSend(sessionId, msg);
      break;
    }
    return result;
  }
}
