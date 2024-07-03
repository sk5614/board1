package com.boot.board.mapper;


import com.boot.board.domain.Board;
import com.boot.board.domain.Search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    
    public List<Board> selectBoardList(@Param("limit") int limit,@Param("offset") int offset);
    //mybatis 에서 변수 못찾아서 @Param 사용 
    public int countBoard();
    
    public void writeBoard(Board board);

	public void deleteBoard(Board board);

	public Board infoBoard(Board board);

	public void editBoard(Board board);

	public void setGroup();

	public void setReply(Board board);

	public void replyBoard(Board board);
	
	public int countBoardbyuser(String username);
	
	public List<Board> searchBoard(@Param("limit") int limit,@Param("offset") int offset, @Param("search") Search search);
	
	public int countSearchBoard(@Param("search") Search search);
	
}