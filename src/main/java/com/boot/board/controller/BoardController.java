package com.boot.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boot.board.domain.Board;
import com.boot.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired BoardService boardservice;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "index";
	}
	
	@GetMapping(value="/list")
	 public String boardList(Model model){ 
		  List<Board> list= boardservice.selectBoardList();  
		  model.addAttribute("boards", list);
		  return "board_list";
	   }
	

}