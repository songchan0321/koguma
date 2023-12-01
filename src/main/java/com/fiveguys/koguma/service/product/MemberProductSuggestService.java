package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.entity.Product;

import java.util.List;

public interface MemberProductSuggestService {

    void addSuggetPrice(MemberProductSuggestDTO memberProductSuggestDTO) throws Exception;
    List<MemberProductSuggestDTO> listSuggestPrice(Product product);
    MemberProductSuggestDTO getSuggestPrice();
}
