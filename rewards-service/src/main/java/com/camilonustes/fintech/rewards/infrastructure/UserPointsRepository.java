package com.camilonustes.fintech.rewards.infrastructure;

import com.camilonustes.fintech.rewards.domain.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, String> {
}