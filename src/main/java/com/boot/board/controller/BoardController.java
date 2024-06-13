package com.boot.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.board.domain.Board;
import com.boot.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardservice;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("인덱스 페이지 호출");
		return "index";
	}

	@GetMapping(value = "/board/list")
	public String boardList(Model model, 
			@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
		List<Board> list = boardservice.selectBoardList(page, size);
		
        int totalBoards = boardservice.countBoard();
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        int startPage = Math.max(1, page - 4);
        int endPage = Math.min(startPage + 5, totalPages);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("boards", list);
        model.addAttribute("size", size);
        model.addAttribute("nowPage", page);  // 현재 페이지 번호
        model.addAttribute("startPage", startPage);  // 시작 페이지 번호
        model.addAttribute("endPage", endPage);  // 끝 페이지 번호
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
        model.addAttribute("pageNumbers", pageNumbers);  // 페이지 번호 목록
		return "board_list";
	}

	@GetMapping(value = "/board/write")
	public String boardWrite() {
		return "board_write";
	}

	@PostMapping(value = "/board/writepro")
	public String boardWritePro(Board board) {
		boardservice.writeBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping(value = "/board/info")
	public String boardInfo(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board_info";
	}

	@GetMapping(value = "/board/delete")
	public String boardDelete(Model model, Board board) {
		boardservice.deleteBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping(value = "/board/edit")
	public String boardEdit(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "board_edit";
	}

	@RequestMapping(value = "/board/editpro")
	public String boardEditPro(Board board) {
		boardservice.editBoard(board);
		return "redirect:/board/info?b_id=" + board.getB_id();
	}

	@GetMapping(value = "/board/reply")
	public String boardReply(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		return "/board_reply";
	}

	@PostMapping(value = "/board/replypro") // 답글
	public String replyBoardPro(Model model, Board board) {
		model.addAttribute("board", boardservice.infoBoard(board));
		boardservice.replyBoard(board);
		return "redirect:/board/list"; // 이전화면으로 리다이렉트
	}

}