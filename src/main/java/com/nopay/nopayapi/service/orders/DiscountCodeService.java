package com.nopay.nopayapi.service.orders;

import com.nopay.nopayapi.dto.orders.DiscountCodeDTO;
import com.nopay.nopayapi.entity.orders.DiscountCode;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.orders.DiscountCodeRepository;
import com.nopay.nopayapi.repository.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountCodeService {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DiscountCodeDTO> getAllDiscountCodes() {
        return discountCodeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DiscountCodeDTO getDiscountCodeById(Integer id) {
        DiscountCode discountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount code not found"));
        return convertToDto(discountCode);
    }

    public DiscountCodeDTO createDiscountCode(DiscountCodeDTO discountCodeDTO) {
        User currentUser = getCurrentUser();
        DiscountCode discountCode = convertToEntity(discountCodeDTO);
        discountCode.setSeller(currentUser);
        discountCode = discountCodeRepository.save(discountCode);
        return convertToDto(discountCode);
    }

    public DiscountCodeDTO activateDiscountCode(Integer id) {
        DiscountCode discountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount code not found"));

        User currentUser = getCurrentUser();

        if (!discountCode.getSeller().getId().equals(currentUser.getId())) {
            throw new SecurityException("You are not authorized to activate this discount code");
        }

        discountCode.setActive(true);
        discountCode = discountCodeRepository.save(discountCode);
        return convertToDto(discountCode);
    }

    public DiscountCodeDTO updateDiscountCode(Integer id, DiscountCodeDTO discountCodeDTO) {
        DiscountCode existingDiscountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount code not found"));

        User currentUser = getCurrentUser();

        if (!existingDiscountCode.getSeller().getId().equals(currentUser.getId())) {
            throw new SecurityException("You are not authorized to modify this discount code");
        }

        existingDiscountCode.setCode(discountCodeDTO.getCode());
        existingDiscountCode.setDiscountAmount(discountCodeDTO.getDiscountAmount());
        existingDiscountCode.setIsWholeOrder(discountCodeDTO.getIsWholeOrder());
        existingDiscountCode.setDescription(discountCodeDTO.getDescription());
        existingDiscountCode.setActive(discountCodeDTO.getActive());

        existingDiscountCode = discountCodeRepository.save(existingDiscountCode);
        return convertToDto(existingDiscountCode);
    }

    public void deleteDiscountCode(Integer id) {
        DiscountCode discountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount code not found"));

        User currentUser = getCurrentUser();

        if (!discountCode.getSeller().getId().equals(currentUser.getId())) {
            throw new SecurityException("You are not authorized to delete this discount code");
        }

        discountCodeRepository.delete(discountCode);
    }

    private User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private DiscountCodeDTO convertToDto(DiscountCode discountCode) {
        return new DiscountCodeDTO(
                discountCode.getId(),
                discountCode.getCode(),
                discountCode.getDiscountAmount(),
                discountCode.getIsWholeOrder(),
                discountCode.getDescription(),
                discountCode.getActive(),
                discountCode.getSeller().getId());
    }

    private DiscountCode convertToEntity(DiscountCodeDTO discountCodeDTO) {
        return new DiscountCode(
                discountCodeDTO.getId(),
                discountCodeDTO.getCode(),
                discountCodeDTO.getDiscountAmount(),
                discountCodeDTO.getIsWholeOrder(),
                discountCodeDTO.getDescription(),
                discountCodeDTO.getActive(),
                null);
    }
}
