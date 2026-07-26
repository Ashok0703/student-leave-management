package com.college.leavemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hod")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}