package com.camilonustes.fintech.rewards.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoints {

    @Id
    private String userId; // Usamos el ID que viene del evento de pago
    private Long totalPoints;
}