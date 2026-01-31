package com.example.demo.controller;

import com.example.demo.model.Feedback;
import com.example.demo.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public String submitFeedback(@RequestParam(required = false) Long userId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            RedirectAttributes ra) {
        Feedback fb = new Feedback();
        fb.setName(name);
        fb.setEmail(email);
        fb.setSubject(subject);
        fb.setMessage(message);
        // userId có thể null, optional
        // TODO: gán User nếu cần
        feedbackService.createFeedback(fb);
        ra.addFlashAttribute("success", "Cảm ơn bạn đã gửi phản hồi!");
        return "redirect:/products-View-details";
    }
}