package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.boot.board.domain.Board;
import com.boot.board.domain.Search;
import com.boot.board.mapper.BoardMapper;
import com.boot.board.util.SessionUtils;

@Service
public class BoardService {

	@Autowired
	BoardMapper boardmapper;
    @Autowired
    private SessionUtils sessionUtils;

//	public List<Board> selectBoardList(int page, int size) {
//		int offset = (page-1)*size;
//		return boardmapper.selectBoardList(size,offset);
//	}

	public int countBoard() {
		return boardmapper.countBoard();
	}
	
	public void writeBoard(Board board) {
		boardmapper.writeBoard(board);
		boardmapper.setGroup();

	}

	public void deleteBoard(Board board) {
		boardmapper.deleteBoard(board);
	}

	public Board infoBoard(Board board) {
		return boardmapper.infoBoard(board);

	}

	public void editBoard(Board board) {
		boardmapper.editBoard(board);
	}

	public void replyBoard(Board board) {
		boardmapper.setReply(board);
		boardmapper.replyBoard(board);
	}
	
	public int countBoardbyuser(String username) {
		return boardmapper.countBoardbyuser(username);
		
	}

	public int countSearchBoard(Search search) {
		return boardmapper.countSearchBoard(search);
	}
	
	public List<Board> searchBoard(int page, int size, Search search) {
		int offset = (page-1)*size;
		return boardmapper.searchBoard(size,offset,search);
	}
	

	 public boolean isAuthor(Board board) {
	        // 현재 인증된 사용자 정보 가져오기
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = null;

	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            username = userDetails.getUsername();
	        }

	        // Board 객체의 작성자 정보 가져오기
	        Board checkWriter = boardmapper.infoBoard(board);
	        return checkWriter != null && checkWriter.getbWriter() != null && checkWriter.getbWriter().equals(username);
	    }
	
}
