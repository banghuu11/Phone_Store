// AdminCommentController.java
package com.example.demo.controller;

import com.example.demo.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService commentService;

    public AdminCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Trả về fragment hiển thị danh sách bình luận
    @GetMapping 
    public String listComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "fragments/comments :: content";
    }

    // Xóa bình luận
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, Model model) {
        commentService.deleteComment(id);
        model.addAttribute("comments", commentService.getAllComments());
        return "fragments/comments :: content";
    }

    // Gửi phản hồi cho bình luận
    @PostMapping("/reply/{id}")
    public String replyToComment(@PathVariable Long id,
            @RequestParam("reply") String reply,
            Model model) {
        commentService.replyComment(id, reply);
        model.addAttribute("comments", commentService.getAllComments());
        return "fragments/comments :: content";
    }
}
