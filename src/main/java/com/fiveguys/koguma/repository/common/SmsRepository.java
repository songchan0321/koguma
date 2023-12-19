package com.fiveguys.koguma.repository.common;

import java.time.Duration;

import com.fiveguys.koguma.data.dto.SmsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SmsRepository {

    /* 인증번호와 관련된 정보들을 저장, 삭제, 조회하기 위해 Repository 생성
     * Redis에 저장하는 부분에서 .set을 쓰는데 중복 방지가 자동으로 된다.
     * 마지막에 발급된 인증번호 하나만 저장된다.
     * 키 중복 불가 -> 즉 하나의 번호에는 하나의 인증 번호 정보만 가지고 있게 된다.
     * 따라서 사용자가 인증번호 발급을 여러 번 눌러 여러 개 발급 받더라도 마지막에 발급된 인증 번호로만 인증 가능하다.
     */

    private final String PHONE_PREFIX = "phone:"; // key값이 중복되지 않도록 상수 선언
    private final int LIMIT_TIME = 30000 * 60000; // 인증번호 유효 시간

    private final StringRedisTemplate stringRedisTemplate;

    // Redis에 저장
    public void addAuthNum(SmsDTO smsDTO) {
        // 사용자의 휴대폰 번호에 대한 키로 "authNum"과 함께 저장
        stringRedisTemplate.opsForValue()
                .set(PHONE_PREFIX + smsDTO.getTo(), smsDTO.getAuthNumber(), Duration.ofSeconds(LIMIT_TIME));
    }

    // 휴대전화 번호에 해당하는 인증번호 불러오기
    public String getAuthNum(String to) {
        return stringRedisTemplate.opsForValue().get(PHONE_PREFIX + to);
    }

    // 인증 완료 시, 인증번호 Redis에서 삭제
    public void deleteAuthNum(String to) {
        stringRedisTemplate.delete(PHONE_PREFIX + to);
    }

    // Redis에 해당 휴대번호로 저장된 인증번호가 존재하는지 확인
    public boolean isAuthNum(String to) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PHONE_PREFIX + to));
    }
}