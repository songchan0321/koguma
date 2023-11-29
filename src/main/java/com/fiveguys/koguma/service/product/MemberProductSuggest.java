package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;

import java.util.List;

public interface MemberProductSuggest {

    void addSuggetPrice();
    List<MemberProductSuggestDTO> listSuggestPrice();
    MemberProductSuggestDTO getSuggestPrice();
}
