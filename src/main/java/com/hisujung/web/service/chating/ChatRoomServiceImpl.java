package com.hisujung.web.service.chating;


import com.hisujung.web.dto.ChatRoomDto;
import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.chating.ChatRoom;
import com.hisujung.web.entity.chating.ChatingJoinInfo;
import com.hisujung.web.jpa.MemberRepository;
import com.hisujung.web.jpa.chating.ChatRoomRepository;
import com.hisujung.web.jpa.chating.ChatingJoinInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatingJoinInfoRepository chatingJoinInfoRepository;
//    private final ChatRoomDto chatRoomDto;

    @Override
    public ChatRoomDto createChatRoom(Member member, String roomName, String roomPwd, int secretChk, int maxUserCnt) {
        String roomId = generateUniqueRoomId(); // 유니크한 채팅방 ID 생

        // ChatRoom 엔티티 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId) // 생성된 roomId 설정
                .roomName(roomName)
                .roomPwd(roomPwd)
                .secretChk(secretChk)
                .maxUserCnt(maxUserCnt)
                .build();

        // ChatRoom 엔티티 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        //join정보 저장
        ChatingJoinInfo chatingJoinInfo = ChatingJoinInfo.builder().chatRoom(chatRoom).member(member).build();
        chatingJoinInfoRepository.save(chatingJoinInfo);

        // ChatRoomDto 생성 및 반환
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId()) // 이미 생성된 chatRoom의 roomId를 사용
                .roomName(chatRoom.getRoomName())
                .userCount(0)  // 새로 생성된 채팅방이므로 초기 인원수는 0으로 설정
                .maxUserCnt(chatRoom.getMaxUserCnt())
                .roomPwd(chatRoom.getRoomPwd())
                .secretChk(chatRoom.getSecretChk())
                // ... 필요한 다른 필드들을 설정해주십시오.
                .build();

        return chatRoomDto;
    }

    //사용자가 입장한 채팅방들 불러오기
    @Override
    public List<ChatRoomDto> getChatRoomList(Member member) {
        List<ChatingJoinInfo> list= chatingJoinInfoRepository.findByMember(member);
        List<ChatRoomDto> resultList = new ArrayList<>();

        for(ChatingJoinInfo c : list) {
            resultList.add(new ChatRoomDto(c.getChatRoom()));
        }

        return resultList;
    }

    // 채팅방에 입장한 사용자 추가
    @Override
    public String addUser(String roomId, String email) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found with ID: " + roomId));

        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        ChatingJoinInfo joinInfo = ChatingJoinInfo.builder()
                .chatRoom(chatRoom)
                .member(user)
                .build();

        chatingJoinInfoRepository.save(joinInfo);

        plusUserCnt(roomId); // 채팅방 인원 추가

        return email;
    }

    private String generateUniqueRoomId() {
        // UUID를 사용하여 유니크한 채팅방 ID 생성
        return UUID.randomUUID().toString();
    }

    @Override
    public ChatRoomDto findChatRoomById(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found with ID: " + roomId));

        // ChatRoom 정보를 ChatRoomDto로 변환하여 반환
        return ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .userCount(0)  // 채팅방 정보만 조회하는 경우에는 초기 인원수를 설정하지 않아도 될 수도 있습니다.
                .maxUserCnt(chatRoom.getMaxUserCnt())
                .roomPwd(chatRoom.getRoomPwd())
                .secretChk(chatRoom.getSecretChk())
                // ... 필요한 다른 필드들을 설정해주십시오.
                .build();
    }

    @Override
    // 채팅방 인원-1
    public void plusUserCnt(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found with ID: " + roomId));

        chatRoom.setUserCount(chatRoom.getUserCount() + 1);

        // ChatRoomDto로 변환
        ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .userCount(0)  // 채팅방 정보만 조회하는 경우에는 초기 인원수를 설정하지 않아도 될 수도 있습니다.
                .maxUserCnt(chatRoom.getMaxUserCnt())
                .roomPwd(chatRoom.getRoomPwd())
                .secretChk(chatRoom.getSecretChk())
                .build();

    }

    @Override
    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found with ID: " + roomId));

        chatRoom.setUserCount(chatRoom.getUserCount() - 1);

        // ChatRoomDto로 변환
        ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .userCount(0)  // 채팅방 정보만 조회하는 경우에는 초기 인원수를 설정하지 않아도 될 수도 있습니다.
                .maxUserCnt(chatRoom.getMaxUserCnt())
                .roomPwd(chatRoom.getRoomPwd())
                .secretChk(chatRoom.getSecretChk())
                .build();

    }

}
