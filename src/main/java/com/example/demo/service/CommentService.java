package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Product;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final ProductRepository productRepo;

    public CommentService(CommentRepository commentRepo, ProductRepository productRepo) {
        this.commentRepo = commentRepo;
        this.productRepo = productRepo;
    }

    public void deleteCommentById(Long id) {
        commentRepo.deleteById(id);
    }

    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepo.findByProductProductId(productId);
    }

    public Comment addComment(Long productId, String username, String content) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product == null)
            return null;
        Comment c = new Comment();
        c.setProduct(product);
        c.setUsername(username);
        c.setContent(content);
        return commentRepo.save(c);
    }

    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }

    public void replyComment(Long id, String reply) {
        commentRepo.findById(id).ifPresent(c -> {
            c.setAdminReply(reply);
            commentRepo.save(c);
        });
    }


    public List<Comment> getAllComments() {
        List<Comment> list = commentRepo.findAll();
        // Khởi tạo để tránh LazyInitializationException khi render Thymeleaf
        list.forEach(c -> {
            if (c.getProduct() != null) {
                c.getProduct().getName();
            }
        });
        return list;
    }
}