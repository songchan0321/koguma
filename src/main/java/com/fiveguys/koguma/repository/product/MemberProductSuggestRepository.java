package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProductSuggestRepository extends JpaRepository<MemberProductSuggest,MemberProductSuggestId> {
}
