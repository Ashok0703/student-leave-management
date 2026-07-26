package com.college.leavemanagement.entity;

import com.college.leavemanagement.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String leaveType;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    private int totalDays;
    private String address;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LeaveStatus tutorStatus = LeaveStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private LeaveStatus hodStatus;

    private String tutorRemarks;
    private String hodRemarks;

    @Column(nullable = false)
    private LocalDate appliedDate;
}