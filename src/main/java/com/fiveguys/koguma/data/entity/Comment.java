package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Comment parent;

   // @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @Builder
    public Comment(Long id, Post post, Member member,
                   Comment parent, String content, Boolean activeFlag){
        this.id = id;
        this.post = post;
        this.member = member;
        this.parent = parent;
        this.content = content;
        this.activeFlag = activeFlag;
    }


    public static Comment createComment(Post post, Member member, String content){
        return Comment.builder()
                .post(post)
                .member(member)
                .content(content)
                .activeFlag(true)
                .build();
    }

    public void updateParent(Comment parent){
        this.parent = parent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

}
