package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubPostService {

    private final PostRepository postRepository;
    private final MemberService memberService;


}
