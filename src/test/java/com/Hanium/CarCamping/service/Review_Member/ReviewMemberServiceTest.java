package com.Hanium.CarCamping.service.Review_Member;

import com.Hanium.CarCamping.Exception.NoSuchMemberException;
import com.Hanium.CarCamping.domain.dto.campsite.CreateCampSiteDto;
import com.Hanium.CarCamping.domain.dto.member.createDto;
import com.Hanium.CarCamping.domain.dto.member.getDto;
import com.Hanium.CarCamping.domain.dto.review.CreateReviewDto;
import com.Hanium.CarCamping.domain.entity.CampSite;
import com.Hanium.CarCamping.domain.entity.Review;
import com.Hanium.CarCamping.domain.entity.Review_Member;
import com.Hanium.CarCamping.domain.entity.member.Member;
import com.Hanium.CarCamping.repository.MemberRepository;
import com.Hanium.CarCamping.service.CampSite.CampsiteService;
import com.Hanium.CarCamping.service.Review.ReviewService;
import com.Hanium.CarCamping.service.member.MemberCreateService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ReviewMemberServiceTest {
    @Autowired MemberCreateService memberCreateService;
    @Autowired MemberRepository memberRepository;
    @Autowired ReviewMemberService reviewMemberService;
    @Autowired ReviewService reviewService;
    @Autowired CampsiteService campsiteService;

    @BeforeEach
    public void setUpData() {
        Member m1 = setUpMember("tester1@naver.com","차박러1");
        Member m2 = setUpMember("tester2@naver.com","차박러2");
        Long campsite_id = campsiteService.saveCampSite(setUpCampSite("안양시 차박지", 5.0f), m1);
        CreateReviewDto reviewDto = setUpReviewDto("좋아요", 5.0f);
        reviewService.saveReview(reviewDto, m1, campsiteService.findById(campsite_id));
    }
    @Test
    public void 좋아요_기능() throws Exception {
        //given
        Member m2 = memberRepository.findByNickname("차박러2").orElseThrow(NoSuchMemberException::new);
        CampSite campsite = campsiteService.findByName("안양시 차박지");
        List<Review> reviewList = reviewService.findByCampSite(campsite.getCampsite_id());

        //when
        Review_Member.createReview_Member(reviewList.get(0), m2, 1);

        //then
        assertThat(reviewMemberService.findByReviewAndMember(reviewList.get(0),m2).get(0).getMember_id()).isEqualTo(m2);
        assertThat(reviewList.get(0).getRecommend()).isEqualTo(1);

    }





    public CreateReviewDto setUpReviewDto(String s, float f) {
        CreateReviewDto ReviewDto = CreateReviewDto.builder()
                .title(s)
                .content("차박지 입니다").
                        score(f).build();
        return ReviewDto;
    }

    public CreateCampSiteDto setUpCampSite(String s, float f) {
        return CreateCampSiteDto.builder()
                .name(s)
                .address("안양시 동안구")
                .explanation("설명")
                .image("htts://www.naver.com")
                .region("경기도")
                .score(f)
                .videoLink("https://youtube.com")
                .build();
    }

    public Member setUpMember(String email, String name) {
        getDto member = memberCreateService.createMember(createDto.builder().
                email(email)
                .password("1234")
                .nickname(name)
                .point(3)
                .build());
        return memberRepository.findByEmail(member.getEmail()).orElseThrow();
    }

}