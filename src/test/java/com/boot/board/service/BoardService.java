package com.boot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.boot.board.domain.Board;
import com.boot.board.mapper.BoardMapper;

public class BoardService {

@Autowired BoardMapper boardmapper;	
	
	  public List<Board> selectBoardList() {
	        return boardmapper.selectBoardList();
	    }
	
}
