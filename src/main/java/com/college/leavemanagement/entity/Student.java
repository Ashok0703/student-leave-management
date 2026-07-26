package com.college.leavemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String regNo;

    private String section;
    private String parentName;
    private String parentMobile;
    private String studentMobile;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}