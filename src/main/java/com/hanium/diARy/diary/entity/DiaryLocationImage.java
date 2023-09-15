package com.hanium.diARy.diary.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.File;

@Data
@Entity
@Table(name="diary_location_image")
public class DiaryLocationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_location_id", nullable = false)
    private DiaryLocation diaryLocation;

    @Column(name = "image_data")
    private String imageData;

    @Column(name = "image_uri")
    private String imageUri;
}
