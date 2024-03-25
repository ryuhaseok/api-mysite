package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.UserService;
import com.javaex.util.JwtUtil;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//로그인
	@PostMapping(value="/api/users/login")
	public UserVo login(@RequestBody UserVo userVo, HttpServletResponse response) {
		System.out.println("UserController.login()");
		
		// no, name              id, pw
		UserVo authUser = userService.exeLogin(userVo);
		System.out.println(authUser);
		
		
		if(authUser != null) {
			//토큰발급 헤더에 실어 보낸다                    문자열과 숫자 더하면 문자열로 바뀐다.
			JwtUtil.createTokenAndSetHeader(response, ""+authUser.getNo());
			
		}
		
		return authUser;
	}

}
