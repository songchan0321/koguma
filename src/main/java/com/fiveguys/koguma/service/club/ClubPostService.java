package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubPostDTO;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubPostService {

    private final PostRepository postRepository;
    private final MemberService memberService;


    public Long addClubPost(ClubPostDTO clubPostDTO, Long clubMemberId){



        return null;
    }

    public Page<Post> listPost(){
        return null;
    }

    public void updatePost(){
    }
    
}
