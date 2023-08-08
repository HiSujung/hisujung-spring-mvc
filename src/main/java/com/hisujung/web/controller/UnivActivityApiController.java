package com.hisujung.web.controller;

import com.hisujung.web.dto.UnivActListResponseDto;
import com.hisujung.web.entity.Member;
import com.hisujung.web.service.BasicMemberService;
import com.hisujung.web.service.UnivActService;
import com.hisujung.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/univactivity")
public class UnivActivityApiController {

    private final UnivActService univActService;
    private final BasicMemberService basicMemberService;
    private final UserService userService;

    //전체 교내 공지사항 조회
    @GetMapping("/")
    public List<UnivActListResponseDto> findAll() {
        return univActService.findAllByDesc();
    }

    //학과별 교내 공지사항 조회
    @GetMapping("/{department}")
    public List<UnivActListResponseDto> findByDepartment(@PathVariable String department) {
        return univActService.findByDepartment(department);
    }

    //제목 키워드별 교내 공지사항 조회
    @GetMapping("/{keyword}")
    public List<UnivActListResponseDto> findByTitle(@PathVariable String keyword) {
        return univActService.findByTitle(keyword);
    }

    //제목 및 학과별 교내 공지사항 조회
    @GetMapping("/{department}/{keyword}")
    public List<UnivActListResponseDto> findByDepAndTitle(@PathVariable String department, @PathVariable String keyword) {
        return univActService.findByDepAndTitle(department, keyword);
    }

    //========회원이 교내 공지사항 좋아요 눌렀을 때========
    @PostMapping(value = {"/", "/{department}", "/{keyword}", "/{department}/{keyword}"})
    public Long saveLike(Authentication auth, @RequestBody Long actId) {
        Member m = userService.getLoginUserByLoginId(auth.getName());
        return univActService.saveLike(actId, m);
    }

    //교내 공지사항 좋아요 취소
    @DeleteMapping(value = {"/{id}", "/{department}/{id}", "/{keyword}/{id}", "/{department}/{keyword}/{id}", "/likelist/{id}"})
    public Long deleteLike(@PathVariable("id") Long id, Authentication auth) {
        Member m = userService.getLoginUserByLoginId(auth.getName());

        univActService.deleteLike(m, id);
        return id;
    }

    //회원의 교내 공지사항 좋아요 목록
    @GetMapping("/likelist")
    public List<UnivActListResponseDto> findByUser(Authentication auth) {
        Member m = userService.getLoginUserByLoginId(auth.getName());
        return univActService.findByUser(m);
    }
}
