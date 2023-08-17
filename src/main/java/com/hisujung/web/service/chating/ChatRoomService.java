package com.hisujung.web.service.chating;

import com.hisujung.web.dto.ChatRoomDto;
import com.hisujung.web.entity.Member;

import java.util.List;

public interface ChatRoomService {

    ChatRoomDto createChatRoom(Member member, String roomName, String roomPwd, int secretChk, int maxUserCnt);
    public ChatRoomDto findChatRoomById(String roomId);
    //    public List<ChatRoomDto> getUserChatRooms(Long userId);
    public List<ChatRoomDto> getChatRoomList(Member member);
    public void plusUserCnt(String roomId);
    public void minusUserCnt(String roomId);
    public String addUser(String roomId, String email);
}

