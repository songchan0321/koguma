package com.fiveguys.koguma.service.product;


import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.repository.product.MemberProductSuggestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberProductSuggestServiceImpl implements MemberProductSuggestService {

    private final MemberProductSuggestRepository memberProductSuggestRepository;


    public void addSuggetPrice(MemberProductSuggestDTO memberProductSuggestDTO) throws Exception {  //buyer 일때 가능
        MemberProductSuggest memberProductSuggest = memberProductSuggestRepository.findByIdMember(memberProductSuggestDTO.toEntity().getId().getMember());
        if (memberProductSuggest!=null)
            throw new Exception("가격제안이 이미 있습니다.");
        memberProductSuggestRepository.save(memberProductSuggestDTO.toEntity());
    }

    public List<MemberProductSuggestDTO> listSuggestPrice(Long productId) {  //seller 일때 가능
        List<MemberProductSuggest> memberProductSuggestList = memberProductSuggestRepository.findAllByIdProductId(productId);

        return memberProductSuggestList.stream().map((c) -> MemberProductSuggestDTO.fromEntity(c))
                .collect(Collectors.toList());
    }


    public MemberProductSuggestDTO getSuggestPrice() {
        return null;
    }
}
