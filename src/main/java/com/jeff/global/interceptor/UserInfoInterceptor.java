package com.jeff.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeff.global.context.UserInfoContext;
import com.jeff.global.error.exception.UnAuthenticationException;

public class UserInfoInterceptor extends HandlerInterceptorAdapter {
	private static final String HEADER_NAME_USER_ID = "X-USER-ID";
	private static final String HEADER_NAME_ROOM_ID = "X-ROOM-ID";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {
		
		String userId = request.getHeader(HEADER_NAME_USER_ID);
		String roomId = request.getHeader(HEADER_NAME_ROOM_ID);
		
		if (Strings.isNotBlank(userId) && Strings.isNotBlank(roomId)) {
			UserInfoContext.setUserInfo(userId, roomId);
			return true;
		}
		
		throw new UnAuthenticationException("유저 정보가 없습니다.");
	}
}
