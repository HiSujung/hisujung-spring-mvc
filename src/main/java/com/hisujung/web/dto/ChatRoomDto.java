package com.hisujung.web.dto;


import com.hisujung.web.entity.chating.ChatRoom;
import lombok.Builder;
import lombok.Data;


// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다!!
// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없고,
// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없다!
@Data
public class ChatRoomDto {
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private int userCount; // 채팅방 인원수
    private int maxUserCnt; // 채팅방 최대 인원 제한

    private String roomPwd; // 채팅방 삭제시 필요한 pwd
    private int secretChk; // 채팅방 잠금 여부

    public ChatRoomDto(ChatRoom entity) {
        this.roomId = entity.getRoomId();
        this.roomName = entity.getRoomName();;
        this.roomPwd = entity.getRoomPwd();
        this.userCount = entity.getUserCount();;
        this.maxUserCnt = entity.getMaxUserCnt();
        this.secretChk = entity.getSecretChk();
    }

    @Builder
    public ChatRoomDto(String roomId, String roomName, String roomPwd, int userCount, int maxUserCnt, int secretChk) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomPwd = roomPwd;
        this.userCount = userCount;
        this.maxUserCnt = maxUserCnt;
        this.secretChk = secretChk;
    }
    // private HashMap<String, String> userlist;

//    public ChatRoom create(String roomName){
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.roomId = UUID.randomUUID().toString();
//        chatRoom.roomName = roomName;
//
//        return chatRoom;
//    }
}