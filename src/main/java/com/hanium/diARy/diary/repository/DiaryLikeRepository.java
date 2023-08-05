package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class DiaryLikeRepository {
    private final DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;

    public DiaryLikeRepository(
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface
    ){
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public void createDiaryLike(Long diaryId, Long userId) {
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.setDiary(this.diaryRepositoryInterface.findById(diaryId).get());
        diaryLike.setUser(this.userRepositoryInterface.findById(userId).get());
        this.diaryLikeRepositoryInterface.save(diaryLike);
    }
/*

        List<DiaryLike> likesList = this.diaryRepositoryInterface.findById(diaryId).get().getDiaryLikes();
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.setDiary(this.diaryRepositoryInterface.findById(diaryId).get());
        diaryLike.setUser(this.userRepositoryInterface.findById(userId).get());
        likesList.add(diaryLike);*/

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

/*    public void updateDiaryLike(DiaryLikeId idDto, DiaryLikeDto dto) {
        Optional<DiaryLike> targetDiaryLike = this.diaryLikeRepositoryInterface.findById(idDto);
        if(targetDiaryLike.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        DiaryLike diaryLike = targetDiaryLike.get();
        diaryLike.setUser();
        diaryLike.setDiary(dto.getDiary());
        this.diaryLikeRepositoryInterface.save(diaryLike);
    }*/

    public void deleteDiaryLike(DiaryLikeDto diaryLikeDto) {
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.setUser(this.userRepositoryInterface.findById(diaryLikeDto.getUserId()).get());
        diaryLike.setDiary(this.diaryRepositoryInterface.findById(diaryLikeDto.getDiaryId()).get());
        this.diaryLikeRepositoryInterface.delete(diaryLike);
    }

/*    public List<DiaryDto> findDiaryLikesByUserId(Long userId) {
        List<Diary> diaryList = this.diaryLikeRepositoryInterface.findByUser_UserId(userId);
        List<DiaryDto> likedDiaries = new ArrayList<>();

        for (Diary diary : diaryList) {
            likedDiaries.add(new DiaryDto(
                    diary.getUser(),
                    diary.getTravelStart(),
                    diary.getContent(),
                    diary.getTags(),
                    diary.getTravelEnd(),
                    diary.getSatisfaction(),
                    diary.getTitle()
            ));
        }

        return likedDiaries;
    }*/
    public List<UserDto> findDiaryLikesByDiaryId(Long diaryId) {
        List<DiaryLike> diaryLikes = diaryLikeRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<UserDto> userDtoList = new ArrayList<>();
        for (DiaryLike like : diaryLikes) {
            User user = like.getUser();
            UserDto userDto = new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getImage());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }
}