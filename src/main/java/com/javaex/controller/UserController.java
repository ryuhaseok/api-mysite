package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.UserService;
import com.javaex.util.JsonResult;
import com.javaex.util.JwtUtil;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	//회원가입
	@PostMapping("api/users/join")
	public JsonResult join(@RequestBody UserVo userVo) {
		System.out.println("UserController.join()");
		
		userService.exeJoin(userVo);
		
		return JsonResult.success("회원가입에 성공했습니다.");
	}
	
	//회원정보 수정
	@PutMapping("/api/users/modify")
	public JsonResult modify(@RequestBody UserVo userVo, HttpServletRequest request) {
		System.out.println("UserController.modify()");
		
		int no = JwtUtil.getNoFromHeader(request);
		if(no != -1) {
			userService.exeModify(userVo);
			
			return JsonResult.success(userVo.getName());
		}else {
			
			return JsonResult.fail("로그인하지않음");
		}
		
	}
	
	//회원정보 수정폼
	@GetMapping("/api/users/modify")
	public JsonResult modifyForm(HttpServletRequest request) {
		System.out.println("UserController.modifyForm()");
		
		/*
		//토큰 가져오기
		String token = JwtUtil.getTokenByHeader(request);
		System.out.println("token=" + token);
		
		//검증
		boolean check = JwtUtil.checkToken(token);
		System.out.println(check);
		
		//이상없음 이상 있음
		if(check == true) {
			System.out.println("정상");
			int no = Integer.parseInt(JwtUtil.getSubjectFromToken(token));
			
			System.out.println(no);
		}
		*/
		
		int no = JwtUtil.getNoFromHeader(request);
		if(no != -1) {
			//정상
			UserVo userVo = userService.exeModifyForm(no);
			System.out.println(userVo);
			return JsonResult.success(userVo);
			
		}else {
			//토큰이 없거나(로그인상태아님), 변조된 경우
			return JsonResult.fail("로그인상태가 아닙니다");
		}
		
	}
	
	//로그인
	@PostMapping(value="/api/users/login")
	public JsonResult login(@RequestBody UserVo userVo, HttpServletResponse response) {
		System.out.println("UserController.login()");
		
		// no, name              id, pw
		UserVo authUser = userService.exeLogin(userVo);
		
		if(authUser != null) {
			//토큰발급 헤더에 실어 보낸다                    문자열과 숫자 더하면 문자열로 바뀐다.
			JwtUtil.createTokenAndSetHeader(response, ""+authUser.getNo());
			return JsonResult.success(authUser);
			
		}else {
			
			return JsonResult.fail("로그인실패");
		}
		
	}

}
