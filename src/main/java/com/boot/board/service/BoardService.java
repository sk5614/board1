package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        String username = sessionUtils.getLoggedInUser();
        Board checkWriter = boardmapper.infoBoard(board);
        return checkWriter != null && checkWriter.getbWriter() != null && checkWriter.getbWriter().equals(username);
    }
	
}
