package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberProductSuggestRepository extends JpaRepository<MemberProductSuggest,MemberProductSuggestId> {
    MemberProductSuggest findByIdMember(Member member);

    List<MemberProductSuggest> findAllByIdProductId(Long productId);
}
