package com.hisujung.web.entity.chating;

import com.hisujung.web.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomId;

    private String roomName;
    private int userCount;
    private int maxUserCnt;

    private String roomPwd;
    private int secretChk;

//    @ManyToMany(mappedBy = "chatRooms", fetch = FetchType.LAZY)
//    private Set<Member> members = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatingJoinInfo> joinList = new ArrayList<>();

    @Builder
    public ChatRoom(Long id, String roomId, String roomName, int userCount, int maxUserCnt, String roomPwd, int secretChk)
    {
        this.id=id;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userCount = userCount;
        this.maxUserCnt = maxUserCnt;
        this.roomPwd = roomPwd;
        this.secretChk = secretChk;
    }

    // ... Constructors, getters, setters, and additional methods as needed
}
