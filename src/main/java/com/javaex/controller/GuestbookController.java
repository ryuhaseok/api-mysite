package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@RestController
public class GuestbookController {
	
	@Autowired
	private GuestService guestService;
	
	
	//삭제
	@DeleteMapping("/api/guestbooks")
	public int delete(@RequestBody GuestVo guestVo) {
		System.out.println("GuestbookController.delete()");
		
		int count = guestService.exeDeleteGuestbook(guestVo);
		
		return count;
	}
	
	//리스트
	@GetMapping("/api/guestbooks")
	public List<GuestVo> list() {
		System.out.println("GuestbookController.list()");
		
		List<GuestVo> guestVo = guestService.exeSelectList();
		
		return guestVo;
	}
	
	//등록
	@PostMapping("/api/guestbooks")
	public int write(@RequestBody GuestVo guestVo) {
		System.out.println("GuestbookController.write()");
		
		int count = guestService.exeInsertGuestbook(guestVo);
		
		return count;
	}
	

}
