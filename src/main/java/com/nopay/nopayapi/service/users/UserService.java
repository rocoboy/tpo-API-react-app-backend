package com.nopay.nopayapi.service.users;

import com.nopay.nopayapi.entity.users.Role;
import com.nopay.nopayapi.entity.users.SellerRequest;
import com.nopay.nopayapi.entity.users.User;
import com.nopay.nopayapi.repository.users.SellerRequestRepository;
import com.nopay.nopayapi.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRequestRepository sellerRequestRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public ResponseEntity<?> deleteById(Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId() == id) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            if (user.getRole() == Role.ADMIN) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("Admin deleted another user successfully");
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("You can't delete another user");
            }
        }
    }

    @Transactional
    public void requestSellerRole() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getRole() == Role.SELLER || user.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("User is already a seller");
        }

        Optional<SellerRequest> sellerRequestOptional = sellerRequestRepository.findById(user.getId());
        if (sellerRequestOptional.isPresent()) {
            throw new IllegalArgumentException("Request already exists");
        }

        SellerRequest sellerRequest = new SellerRequest();
        sellerRequest.setUser(user);
        sellerRequestRepository.save(sellerRequest);

    }

    @Transactional
    public void approveSellerRole(Integer userId) {

        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("User is not an admin");
        }

        Optional<SellerRequest> sellerRequestOptional = sellerRequestRepository.findById(userId);
        if (sellerRequestOptional.isPresent()) {
            SellerRequest sellerRequest = sellerRequestOptional.get();
            User user = sellerRequest.getUser();
            user.setRole(Role.SELLER);
            sellerRequest.setStatus(SellerRequest.RequestStatus.APPROVED);
            userRepository.save(user);
            sellerRequestRepository.save(sellerRequest);
        } else {
            throw new IllegalArgumentException("Invalid Request ID");
        }
    }

    @Transactional
    public void rejectSellerRole(Integer userId) {

        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("User is not an admin");
        }

        Optional<SellerRequest> sellerRequestOptional = sellerRequestRepository.findById(userId);
        if (sellerRequestOptional.isPresent()) {
            SellerRequest sellerRequest = sellerRequestOptional.get();
            sellerRequest.setStatus(SellerRequest.RequestStatus.REJECTED);
            sellerRequestRepository.save(sellerRequest);
        } else {
            throw new IllegalArgumentException("Invalid Request ID");
        }
    }

    public boolean isSeller(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> user.getRole() == Role.SELLER).orElse(false);
    }
}
