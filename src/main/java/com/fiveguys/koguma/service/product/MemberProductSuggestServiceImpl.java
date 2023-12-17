package com.fiveguys.koguma.service.product;


import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberProductSuggestDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
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

        Optional<MemberProductSuggest> memberProductSuggest = memberProductSuggestRepository.findById(memberProductSuggestDTO.toEntity().getId());
        if (memberProductSuggest.isPresent())
            throw new Exception("가격제안이 이미 있습니다.");
        memberProductSuggestRepository.save(memberProductSuggestDTO.toEntity());
    }

    public List<MemberProductSuggestDTO> listSuggestPrice(Long productId) {  //seller 일때 가능

        List<MemberProductSuggest> memberProductSuggestList = memberProductSuggestRepository.findAllByIdProductId(productId);


        List<MemberProductSuggestDTO> list = memberProductSuggestList.stream().map(MemberProductSuggestDTO::fromEntity).collect(Collectors.toList());
        return list;
    }

    @Override
    public int getSuggestPrice(Long suggestId) {
        return 0;
    }

    @Override
    public MemberDTO getSuggestMember(Long suggestId) {
        return null;
    }

    @Override
    public ProductDTO getSuggestProduct(Long suggestId) {
        return null;
    }

//    @Override
//    public int getSuggestPrice(Long suggestId) {
//        return memberProductSuggestRepository.findByMemberProductSuggestId(suggestId).getPrice();
//    }
//
//    @Override
//    public MemberDTO getSuggestMember(Long suggestId) {
//        return MemberDTO.fromEntity(memberProductSuggestRepository.findByMemberProductSuggestId(suggestId).getId().getMember());
//    }
//
//    @Override
//    public ProductDTO getSuggestProduct(Long suggestId) {
//        return ProductDTO.fromEntity(memberProductSuggestRepository.findByMemberProductSuggestId(suggestId).getId().getProduct());
//    }



}
