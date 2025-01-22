package com.noveltools.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "novels")
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
} 