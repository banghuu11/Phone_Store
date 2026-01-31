package com.example.demo.controller;

import com.example.demo.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String addComment(@RequestParam("productId") Long productId,
                             @RequestParam("content") String content,
                             Principal principal) {
        String username = principal != null ? principal.getName() : "Khách";
        commentService.addComment(productId, username, content);
        return "redirect:/detail/product/" + productId + "#comments";
    }
}
