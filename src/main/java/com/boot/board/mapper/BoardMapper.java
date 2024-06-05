package com.boot.board.mapper;


import com.boot.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardMapper {
    
    List<Board> selectBoardList();
    
}