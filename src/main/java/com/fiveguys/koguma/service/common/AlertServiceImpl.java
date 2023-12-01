package com.fiveguys.koguma.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.AlertDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

// issue : redis key -> redis hashKey
@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService{
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public Long addAlert(MemberDTO memberDTO, String title, String content, String url) throws JsonProcessingException {
        AlertDTO alertDTO = AlertDTO.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .content(content)
                .memberId(memberDTO.getId().toString())
                .url(url)
                .regDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        redisTemplate.opsForList().rightPush(memberDTO.getId().toString(), objectMapper.writeValueAsString(alertDTO));
        return null;
    }

    @Override
    public List<AlertDTO> listAlert(MemberDTO memberDTO) {
        List<String> stringAlertList = redisTemplate.opsForList().range(memberDTO.getId().toString(), 0, -1);
        List<AlertDTO> alertDTOList = stringAlertList.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, AlertDTO.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return alertDTOList;
    }

    @Override
    public void readAlert(MemberDTO memberDTO, String id) {
        List<String> alerts = redisTemplate.opsForList().range(memberDTO.getId().toString(), 0, -1);
        this.readAlertAll(memberDTO); // issue
        alerts.removeIf(json -> {
            try {
                AlertDTO alertDTO = objectMapper.readValue(json, AlertDTO.class);
                System.out.println(alertDTO);
                return alertDTO.getId().equals(id);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        });

        redisTemplate.opsForList().rightPushAll(memberDTO.getId().toString(), alerts.stream().collect(Collectors.toList()));
    }

    @Override
    public void readAlertAll(MemberDTO memberDTO) {
        redisTemplate.delete(memberDTO.getId().toString());
    }
}
