package com.hanium.diARy.diary.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
}
