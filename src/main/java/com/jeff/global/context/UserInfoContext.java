package com.jeff.global.context;

import lombok.Getter;

public class UserInfoContext {
	
	private static final ThreadLocal<UserInfo> CONTEXT = new ThreadLocal<>();
	
	public static void setUserInfo(String userId, String roomId) {
		CONTEXT.set(new UserInfo(userId, roomId));
	}
	
	public static UserInfo getUserInfo() {
		return CONTEXT.get();
	}

	@Getter
	public static class UserInfo {
		private final String userId;
		private final String roomId;
		
		public UserInfo(String userId, String roomId) {
			this.userId = userId;
			this.roomId = roomId;
		}
	}
}
