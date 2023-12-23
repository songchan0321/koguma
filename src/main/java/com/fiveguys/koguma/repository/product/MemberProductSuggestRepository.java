package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberProductSuggestRepository extends JpaRepository<MemberProductSuggest,MemberProductSuggestId> {
    Optional<MemberProductSuggest> findById(MemberProductSuggestId memberProductSuggestId);

    List<MemberProductSuggest> findAllByIdProductId(Long productId);

    MemberProductSuggest findByIdProductIdAndIdMemberId(Long productId,Long memberId);

    int countByIdProductId(Long productId);


    Boolean existsByIdProductIdAndIdMemberId(Long productId, Long memberId);
}
