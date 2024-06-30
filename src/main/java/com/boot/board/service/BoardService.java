package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.board.domain.Board;
import com.boot.board.domain.User;
import com.boot.board.mapper.BoardMapper;

@Service
public class BoardService {

	@Autowired
	BoardMapper boardmapper;

	public List<Board> selectBoardList(int page, int size) {
		int offset = (page-1)*size;
		return boardmapper.selectBoardList(size,offset);
	}

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
		boardmapper.replyBoard(board);
		boardmapper.setReply(board);
	}
	
	public int countBoardbyuser(String username) {
		return boardmapper.countBoardbyuser(username);
		
	}

}
