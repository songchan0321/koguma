package com.fiveguys.koguma.service.product;


import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.repository.product.MemberProductSuggestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberProductSuggestImpl implements MemberProductSuggest {

    private final MemberProductSuggestRepository memberProductSuggestRepository;


    @Override
    public void addSuggetPrice() {

    }

    @Override
    public List<MemberProductSuggestDTO> listSuggestPrice() {
        return null;
    }

    @Override
    public MemberProductSuggestDTO getSuggestPrice() {
        return null;
    }
}
