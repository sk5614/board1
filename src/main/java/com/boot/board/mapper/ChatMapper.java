package com.boot.board.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.boot.board.domain.ChatMessage;

@Mapper
public interface ChatMapper {

    @Insert("INSERT INTO chat_messages (content, sender, type) VALUES (#{content}, #{sender}, #{type})")
    void saveChatMessage(ChatMessage chatMessage);
}
