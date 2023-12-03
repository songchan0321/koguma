package com.fiveguys.koguma.repository.product;

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
}
