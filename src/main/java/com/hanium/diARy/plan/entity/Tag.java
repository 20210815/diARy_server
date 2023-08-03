package com.hanium.diARy.plan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY) // Plan과의 관계에서 지연 로딩으로 설정
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "name", length = 10)
    private String name;
}
