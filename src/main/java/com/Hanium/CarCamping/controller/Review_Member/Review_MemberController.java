package com.Hanium.CarCamping.controller.Review_Member;

import com.Hanium.CarCamping.config.security.jwt.JwtService;
import com.Hanium.CarCamping.domain.entity.Review;
import com.Hanium.CarCamping.domain.entity.member.Member;
import com.Hanium.CarCamping.service.Review.ReviewService;
import com.Hanium.CarCamping.service.Review_Member.ReviewMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Review_MemberController {
    private final ReviewMemberService reviewMemberService;
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @GetMapping("/review/{id}/up")
    public void upReview(@RequestParam("token") String token, @PathVariable Long id) {
        Review review = reviewService.getReview(id);
        Member member = jwtService.findMemberByToken(token);
        reviewMemberService.createReviewMember(review,member);
    }
    @GetMapping("/review/{id}/down")
    public void downReview(@RequestParam("token") String token, @PathVariable Long id) {
        Review review = reviewService.getReview(id);
        Member member = jwtService.findMemberByToken(token);
        reviewMemberService.createReviewMember(review,member);
    }
}