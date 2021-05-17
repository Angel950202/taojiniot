package com.taojin.iot.transmit.lib.exception;

import com.taojin.iot.transmit.lib.ServerStatus;

public class ServerStatusException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ServerStatusException(ServerStatus status) {
		super("服务状态错误,当前状态："+status.name());
	}
}
