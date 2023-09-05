package com.hanium.diARy.diary.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "DiaryTag")
public class DiaryTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Diary> diaries = new ArrayList<>();

    @Column(name="number_of_diaries")
    private Integer number;

    @Column(name = "name", length = 10)
    private String name;


    @Override
    public String toString() {
        return "DiaryTag{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                '}';
    }

}
