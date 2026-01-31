package com.example.demo.service;

import com.example.demo.model.Feedback;
import com.example.demo.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepo;

    public FeedbackService(FeedbackRepository feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

    public java.util.List<Feedback> getAll() {
        return feedbackRepo.findAll();
    }

    public void reply(Long id, String reply) {
        feedbackRepo.findById(id).ifPresent(f -> {
            f.setAdminReply(reply);
            f.setStatus(1);
            feedbackRepo.save(f);
        });
    }
}