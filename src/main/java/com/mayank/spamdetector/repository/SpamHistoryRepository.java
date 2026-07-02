package com.mayank.spamdetector.repository;

import com.mayank.spamdetector.entity.SpamHistory;
import com.mayank.spamdetector.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpamHistoryRepository extends JpaRepository<SpamHistory, Long> {

    List<SpamHistory> findByUser(User user);

    List<SpamHistory> findByPrediction(String prediction);

}