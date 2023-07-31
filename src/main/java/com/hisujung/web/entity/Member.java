package com.hisujung.web.entity;

import com.hisujung.web.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, unique = true)
    private String email;

    private String userName;

    private String password;

    private String department1;

    private String department2;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Portfolio> portfolioList = new ArrayList<>();

    public boolean checkPassword(PasswordEncoder passwordEncoder, String inputPassword) {
        return passwordEncoder.matches(inputPassword, this.password);
    }

    public String getUsername() {return userName;}

    public void addUserAuthority() {
        this.role = Role.USER;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
//
//package HiSujung.HiSujung_Backend2.entity;
//
//import HiSujung.HiSujung_Backend2.BaseTimeEntity;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder
//@Entity
//public class Member extends BaseTimeEntity {
//
//    @Id @Column(name = "member_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 45, unique = true)
//    private String email;
//
//    private String userName;
//
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    public void addUserAuthority() {
//        this.role = Role.USER;
//    }
//
//    public void encodePassword(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(password);
//    }
//}
