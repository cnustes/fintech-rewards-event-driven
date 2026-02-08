package com.camilonustes.fintech.rewards.api;

public record UserPointsResponse(
        String userId,
        Long totalPoints,
        String status
) {}