package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ======================= LẤY DỮ LIỆU NGƯỜI DÙNG =========================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // **THÊM METHOD NÀY** - Cần thiết cho HomeController
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null ? user.getUserId() : null;
    }

    public User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        String username = auth.getName();
        return findByUsername(username);
    }

    // **THÊM METHOD NÀY** - Lấy userId của user hiện tại
    public Long getCurrentUserId() {
        User currentUser = getCurrentAuthenticatedUser();
        return currentUser != null ? currentUser.getUserId() : null;
    }

    // ======================= TẠO & CẬP NHẬT NGƯỜI DÙNG =======================

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());

            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            user.setFullName(userDetails.getFullName());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setAvatar(userDetails.getAvatar());
            user.setRole(userDetails.getRole());
            user.setStatus(userDetails.getStatus());

            return userRepository.save(user);
        }
        return null;
    }

    public User save(User updatedUser) {
        User existing = userRepository.findById(updatedUser.getUserId()).orElse(null);
        if (existing == null)
            return null;

        // Giữ lại các thông tin không được cập nhật
        updatedUser.setUsername(existing.getUsername());
        updatedUser.setEmail(existing.getEmail());
        updatedUser.setRole(existing.getRole());
        updatedUser.setStatus(existing.getStatus());
        updatedUser.setCreatedAt(existing.getCreatedAt());

        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existing.getPassword());
        } else if (!updatedUser.getPassword().equals(existing.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(updatedUser);
    }

    // ======================= XÓA NGƯỜI DÙNG =======================

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ======================= TÌM KIẾM NGƯỜI DÙNG =======================

    public List<User> searchByKeyword(String keyword) {
        return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                keyword, keyword, keyword);
    }
}