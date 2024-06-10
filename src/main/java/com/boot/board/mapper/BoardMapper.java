package com.boot.board.mapper;


import com.boot.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardMapper {
    
    public List<Board> selectBoardList();
    
    public void writeBoard(Board board);

	public void deleteBoard(Board board);

	public Board infoBoard(Board board);

	public void editBoard(Board board);

}