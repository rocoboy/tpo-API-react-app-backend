package com.nopay.nopayapi.service.orders;

import com.nopay.nopayapi.dto.orders.DiscountCodeDTO;
import com.nopay.nopayapi.entity.orders.DiscountCode;
import com.nopay.nopayapi.repository.orders.DiscountCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    public List<DiscountCodeDTO> getAllDiscountCodes() {
        return discountCodeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DiscountCodeDTO getDiscountCodeById(Integer id) {
        return discountCodeRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Discount code not found"));
    }

    public DiscountCodeDTO createDiscountCode(DiscountCodeDTO discountCodeDTO) {
        DiscountCode discountCode = convertToEntity(discountCodeDTO);
        discountCode = discountCodeRepository.save(discountCode);
        return convertToDTO(discountCode);
    }

    public DiscountCodeDTO updateDiscountCode(Integer id, DiscountCodeDTO discountCodeDTO) {
        DiscountCode discountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount code not found"));

        discountCode.setCode(discountCodeDTO.getCode());
        discountCode.setDiscountAmount(discountCodeDTO.getDiscountAmount());
        discountCode.setIsWholeOrder(discountCodeDTO.getIsWholeOrder());
        discountCode.setDescription(discountCodeDTO.getDescription());

        discountCode = discountCodeRepository.save(discountCode);
        return convertToDTO(discountCode);
    }

    public void deleteDiscountCode(Integer id) {
        discountCodeRepository.deleteById(id);
    }

    private DiscountCodeDTO convertToDTO(DiscountCode discountCode) {
        return new DiscountCodeDTO(
                discountCode.getId(),
                discountCode.getCode(),
                discountCode.getDiscountAmount(),
                discountCode.getIsWholeOrder(),
                discountCode.getDescription());
    }

    private DiscountCode convertToEntity(DiscountCodeDTO discountCodeDTO) {
        return new DiscountCode(
                discountCodeDTO.getId(),
                discountCodeDTO.getCode(),
                discountCodeDTO.getDiscountAmount(),
                discountCodeDTO.getIsWholeOrder(),
                discountCodeDTO.getDescription());
    }
}
