package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentcomment;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean activeFlag;


    @Builder
    public Comment(Long id, Post post, Member member,
                   Comment parentcomment, String content, Boolean activeFlag){
        this.id = id;
        this.post = post;
        this.member = member;
        this.parentcomment = parentcomment;
        this.content = content;
        this.activeFlag = activeFlag;
    }


    public void setParentComment(Comment entity) {
    }
}
