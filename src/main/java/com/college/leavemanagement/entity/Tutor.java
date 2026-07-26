package com.college.leavemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "hod_id", nullable = false)
    private Hod hod;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}