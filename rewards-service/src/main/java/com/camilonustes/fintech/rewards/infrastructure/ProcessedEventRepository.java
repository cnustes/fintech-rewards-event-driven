package com.camilonustes.fintech.rewards.infrastructure;

import com.camilonustes.fintech.rewards.domain.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
    // We don't need extra methods, JpaRepository already gives us .existsById() and
    // .save()
}