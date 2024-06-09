package com.boot.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping(value="/board/list")
	 public String boardList(Model model){ 
		  List<Board> list= boardservice.selectBoardList();  
		  model.addAttribute("boards", list);
		  return "board_list";
	   }
	
	@GetMapping(value="/board/write")
	 public String boardWrite(){ 
		  return "board_write";
	   }
	
	@PostMapping(value="/board/writepro")
	 public String boardWritePro(Board board){
		boardservice.writeBoard(board);
		 return "redirect:/board/list";
	   }
	
	@GetMapping(value="/board/info")
	public String boardInfo(Model model,int id) {
		model.addAttribute("board", boardservice.infoBoard(id));
		return "board_info";
	}
	
	@GetMapping(value="/board/delete")
	public String boardDelete(Model model,int id) {
		boardservice.deleteBoard(id);
		return "redirect:/board/list";
	}
	
	@GetMapping(value="/board/edit")
	 public String boardEdit(Model model,Board board){
		model.addAttribute("board", boardservice.infoBoard(board.getB_id()));
		  return "board_edit";
	   }
	
	@RequestMapping(value="/board/editpro")
	 public String boardEditPro(Board board){
		boardservice.editBoard(board);
		 return "redirect:/board/info"+board.getB_id();
	   }
	

}