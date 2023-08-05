package com.hisujung.web.service;

import com.hisujung.web.dto.LikeUnivActRequestDto;
import com.hisujung.web.dto.UnivActListResponseDto;
import com.hisujung.web.entity.LikeUnivAct;
import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.UnivActivity;
import com.hisujung.web.jpa.LikeUnivActRepository;
import com.hisujung.web.jpa.UnivActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UnivActService {

    private final UnivActivityRepository univActivityRepository;
    private final LikeUnivActRepository likeUnivActRepository;

    //모든 교내 공지사항 조회
    public List<UnivActListResponseDto> findAllByDesc() {
        return univActivityRepository.findAll().stream().map(UnivActListResponseDto::new).collect(Collectors.toList());
    }

    //해당되는 교내 공지사항 조회
    public UnivActListResponseDto findById(Long id) {
        UnivActivity entity = univActivityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 교내 공지사항을 찾을 수 없습니다."));

        return new UnivActListResponseDto(entity);
    }

    //교내 공지사항 학과별로 조회
    public List<UnivActListResponseDto> findByDepartment(String department) {
        return univActivityRepository.findByPostDepartment(department).stream().map(UnivActListResponseDto::new).collect(Collectors.toList());
    }

    //교내 공지사항 제목으로 조회
    public List<UnivActListResponseDto> findByTitle(String keyword) {
        return univActivityRepository.findByTitleContaining(keyword).stream().map(UnivActListResponseDto::new).collect(Collectors.toList());
    }

    //교내 공지사항 학과와 제목으로 조회
    public List<UnivActListResponseDto> findByDepAndTitle(String department, String keyword) {
        return univActivityRepository.findByDepartmentAndTitle(department, keyword).stream().map(UnivActListResponseDto::new).collect(Collectors.toList());
    }

    //============= 회원이 교내활동 좋아요 버튼 눌렀을 때 =============

   @Transactional
    public Long saveLike(Long univActId, Member member) {
        UnivActivity a = univActivityRepository.findById(univActId).orElseThrow();
        LikeUnivActRequestDto dto = LikeUnivActRequestDto.builder()
                .member(member)
                .univActivity(a)
                .build();
        return likeUnivActRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    //교내 공지사항 좋아요 취소
    public void deleteLike(Long id) {
        LikeUnivAct likeUnivAct = likeUnivActRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 좋아요 항목이 없습니다.")));
        likeUnivActRepository.delete(likeUnivAct);
    }

    //회원의 교내 공지사항 좋아요 목록 조회
    public List<UnivActListResponseDto> findByUser(Member member) {
        List<LikeUnivAct> likeList = likeUnivActRepository.findByMember(member);
        List<UnivActListResponseDto> resultList = new ArrayList<>();
        for(LikeUnivAct a: likeList) {
            UnivActivity u = a.getUnivActivity();
            resultList.add(new UnivActListResponseDto(u));
        }
        return resultList;
    }

}
