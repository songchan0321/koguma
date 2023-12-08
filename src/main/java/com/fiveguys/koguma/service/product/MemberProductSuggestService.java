package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberProductSuggestService {

    void addSuggetPrice(MemberProductSuggestDTO memberProductSuggestDTO) throws Exception;
    Page<MemberProductSuggest> listSuggestPrice(Long productId, int page);
    MemberProductSuggestDTO getSuggestPrice();
}
