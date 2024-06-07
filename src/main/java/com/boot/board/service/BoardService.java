package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.board.domain.Board;
import com.boot.board.mapper.BoardMapper;

@Service
public class BoardService {

@Autowired BoardMapper boardmapper;	
	
	  public List<Board> selectBoardList() {
	        return boardmapper.selectBoardList();
	    }
	

	public void writeBoard(Board board) {
		boardmapper.writeBoard(board);
		
	}


	public void deleteBoard(int id) {
		boardmapper.deleteBoard(id);
	}


	public Board infoBoard(int id) {
		return boardmapper.infoBoard(id);
		
	}




}	
