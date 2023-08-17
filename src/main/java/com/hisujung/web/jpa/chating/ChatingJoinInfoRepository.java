package com.hisujung.web.jpa.chating;

import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.chating.ChatingJoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatingJoinInfoRepository extends JpaRepository<ChatingJoinInfo, Long> {
    List<ChatingJoinInfo> findByMember(Member member);
}
