package com.mayank.spamdetector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "spam_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpamHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false, length = 20)
    private String prediction;

    @Column(nullable = false)
    private Double confidenceScore;

    @Column(length = 50)
    private String modelName;

    @Column(nullable = false)
    private Long processingTime;

    @Column(nullable = false)
    private LocalDateTime predictedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void onCreate() {
        predictedAt = LocalDateTime.now();
    }

}