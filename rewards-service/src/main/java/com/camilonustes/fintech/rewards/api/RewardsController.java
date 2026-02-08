package com.camilonustes.fintech.rewards.api;

import com.camilonustes.fintech.rewards.infrastructure.UserPointsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final UserPointsRepository pointsRepository;

    @Cacheable(value = "userPoints", key = "#a0")
    @GetMapping("/{userId}")
    public ResponseEntity<UserPointsResponse> getPoints(@PathVariable("userId") String userId) {
        return pointsRepository.findById(userId)
                .map(points -> ResponseEntity.ok(new UserPointsResponse(userId, points.getTotalPoints(), "ACTIVE")))
                .orElse(ResponseEntity.notFound().build());
    }
}