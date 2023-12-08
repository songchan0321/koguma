package com.fiveguys.koguma.service.product;


import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.repository.product.MemberProductSuggestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberProductSuggestServiceImpl implements MemberProductSuggestService {

    private final MemberProductSuggestRepository memberProductSuggestRepository;


    public void addSuggetPrice(MemberProductSuggestDTO memberProductSuggestDTO) throws Exception {  //buyer 일때 가능
        System.out.println(memberProductSuggestDTO.toEntity().getId());
        Optional<MemberProductSuggest> memberProductSuggest = memberProductSuggestRepository.findById(memberProductSuggestDTO.toEntity().getId());
        if (memberProductSuggest.isPresent())
            throw new Exception("가격제안이 이미 있습니다.");
        memberProductSuggestRepository.save(memberProductSuggestDTO.toEntity());
    }

    public Page<MemberProductSuggest> listSuggestPrice(Long productId, int page) {  //seller 일때 가능

        List<MemberProductSuggest> memberProductSuggestList = memberProductSuggestRepository.findAllByIdProductId(productId);

        Pageable pageable = PageRequest.of(page,9);

        memberProductSuggestList = memberProductSuggestList.stream().collect(Collectors.toList());
        Page<MemberProductSuggest> list = new PageImpl<>(memberProductSuggestList, pageable, memberProductSuggestList.size());

        return list;
    }


    public MemberProductSuggestDTO getSuggestPrice() {
        return null;
    }
}
