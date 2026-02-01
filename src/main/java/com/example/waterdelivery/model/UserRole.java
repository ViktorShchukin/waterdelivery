package com.example.waterdelivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}