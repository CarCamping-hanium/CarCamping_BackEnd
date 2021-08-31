package com.Hanium.CarCamping.service.member;

import com.Hanium.CarCamping.Exception.DuplicatedNickNameException;
import com.Hanium.CarCamping.Exception.NoSuchMemberException;
import com.Hanium.CarCamping.Exception.WrongPasswordException;
import com.Hanium.CarCamping.domain.dto.member.UpdateNickNameDto;
import com.Hanium.CarCamping.domain.dto.member.UpdatePasswordDto;
import com.Hanium.CarCamping.domain.entity.member.Member;
import com.Hanium.CarCamping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberUpdateService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    public void memberNicknameUpdate(UpdateNickNameDto updateDto, String email){
        if (memberRepository.existsByNickname((updateDto.getNickname()))) {
            throw new DuplicatedNickNameException();
        }
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        redisTemplate.opsForZSet().remove("ranking",member.getNickname());
        member.setNickname(updateDto.getNickname());
        redisTemplate.opsForZSet().add("ranking",member.getNickname(), member.getPoint());


    }
    public void memberPasswordUpdate(UpdatePasswordDto updatePasswordDto,String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), member.getPassword())) {
            throw new WrongPasswordException();
        }
        member.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

    }
}
