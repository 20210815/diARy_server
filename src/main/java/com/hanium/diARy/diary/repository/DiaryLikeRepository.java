package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class DiaryLikeRepository {
    private DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;

    public DiaryLikeRepository(
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface
    ){
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
    }

    public void createDiaryLike(DiaryLikeDto dto) {
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.setDiary(dto.getDiary());
        diaryLike.setUser(dto.getUser());
        this.diaryLikeRepositoryInterface.save(diaryLike);
    }

    public DiaryLike readDiaryLike(DiaryLikeId idDto) {
        Optional<DiaryLike> diaryLike = this.diaryLikeRepositoryInterface.findById(idDto);
        if(diaryLike.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return diaryLike.get();
    }

    public Iterator<DiaryLike> readDiaryLikeAll() {
        return this.diaryLikeRepositoryInterface.findAll().iterator();
    }

    public void updateDiaryLike(DiaryLikeId idDto, DiaryLikeDto dto) {
        Optional<DiaryLike> targetDiaryLike = this.diaryLikeRepositoryInterface.findById(idDto);
        if(targetDiaryLike.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        DiaryLike diaryLike = targetDiaryLike.get();
        diaryLike.setUser(dto.getUser());
        diaryLike.setDiary(dto.getDiary());
        this.diaryLikeRepositoryInterface.save(diaryLike);
    }

    public void deleteDiaryLike(DiaryLikeId idDto) {
        Optional<DiaryLike> targetDiaryLike = this.diaryLikeRepositoryInterface.findById(idDto);
        if(targetDiaryLike.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.diaryLikeRepositoryInterface.delete(targetDiaryLike.get());
    }

    public List<DiaryLike> findDiaryLikesByUserId(Long userId) {
        return this.diaryLikeRepositoryInterface.findByUser_UserId(userId);
    }

    public List<DiaryLike> findDiaryLikesByDiaryId(Long diaryId) {
        return this.diaryLikeRepositoryInterface.findByDiary_DiaryId(diaryId);
    }
}