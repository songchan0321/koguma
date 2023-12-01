package com.fiveguys.koguma.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table(name = "member_relationships")
@NoArgsConstructor()

public class MemberRelationship extends BaseTime {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_member_id")
    private Member sourceMemberId;

    @ManyToOne
    @JoinColumn(name = "target_member_id")
    private Member targetMemberId;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_relation_type")
    private MemberRelationshipType memberRelationshipType;


    @Builder
    public MemberRelationship (Member sourceMemberId, Member targetMemberId, String content, MemberRelationshipType memberRelationshipType) {
        this.sourceMemberId = sourceMemberId;
        this.targetMemberId = targetMemberId;
        this.content = content;
        this.memberRelationshipType = memberRelationshipType;

    }
}

