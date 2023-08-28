package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.DiaryLikeMapper;
import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLikeDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLike;
import com.hanium.diARy.diary.entity.DiaryLikeId;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Repository
public class DiaryLikeRepository {
    private final DiaryLikeRepositoryInterface diaryLikeRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final CommentRepository commentRepository;

    public DiaryLikeRepository(
            @Autowired DiaryLikeRepositoryInterface diaryLikeRepositoryInterface,
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired CommentRepository commentRepository
            ){
        this.diaryLikeRepositoryInterface = diaryLikeRepositoryInterface;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.commentRepository = commentRepository;
    }

    public void createDiaryLike(Long diaryId) {
        DiaryLike diaryLike = new DiaryLike();
        Diary diary = this.diaryRepositoryInterface.findById(diaryId).get();
        diaryLike.setDiary(diary);
        User user = new User();
        user.setUserId(1L);
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String email = authentication.getName();
        //User user = userRepositoryInterface.findByEmail(email);
        diaryLike.setUser(user);
        List<DiaryLike> diaryLikes = diary.getDiaryLikes();
        diaryLikes.add(diaryLike);
        diary.setDiaryLikes(diaryLikes);
        this.diaryRepositoryInterface.save(diary);
        this.diaryLikeRepositoryInterface.save(diaryLike);
    }
/*

        List<DiaryLike> likesList = this.diaryRepositoryInterface.findById(diaryId).get().getDiaryLikes();
        DiaryLike diaryLike = new DiaryLike();
        diaryLike.setDiary(this.diaryRepositoryInterface.findById(diaryId).get());
        diaryLike.setUser(this.userRepositoryInterface.findById(userId).get());
        likesList.add(diaryLike);*/

    public List<DiaryLikeDto> readDiaryLike(Long diaryId) {
        Diary diary = this.diaryRepositoryInterface.findById(diaryId).get();
        List<DiaryLike> diaryLikes = diary.getDiaryLikes();
        List<DiaryLikeDto> diaryLikeDtos = new ArrayList<>();
        for(DiaryLike diaryLike : diaryLikes) {
            DiaryLikeDto diaryLikeDto = new DiaryLikeDto();
            diaryLikeDto.setUserId(diaryLike.getUser().getUserId());
            diaryLikeDto.setDiaryId(diaryLike.getDiary().getDiaryId());
            diaryLikeDtos.add(diaryLikeDto);
        }
        return diaryLikeDtos;
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

    public void deleteDiaryLike(Long diaryId) {
        User user = new User();
        user.setUserId(1L);
        DiaryLike diaryLike = this.diaryLikeRepositoryInterface.findByUser_UserIdAndDiary_DiaryId(user.getUserId(), diaryId);
        this.diaryLikeRepositoryInterface.delete(diaryLike);
    }

    public List<DiaryDto> findDiaryLikesByUserId(Long userId) {
        List<DiaryLike> diaryList = this.diaryLikeRepositoryInterface.findByUser_UserId(userId);
        List<DiaryDto> likedDiaries = new ArrayList<>();


        for (DiaryLike diary : diaryList) {
            DiaryDto diaryDto = new DiaryDto();
            this.diaryRepositoryInterface.findById(diary.getDiary().getDiaryId());
            BeanUtils.copyProperties(diary, diaryDto);
            diaryDto.setComments(commentRepository.readDiaryCommentAll(diary.getDiary().getDiaryId()));
            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diary.getDiary().getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            diaryDto.setTags(tagDtos);
            diaryDto.setLikes(this.readDiaryLike(diary.getDiary().getDiaryId()));
//            diary.getDiary();
//            //Diary가 나옴 이걸 DTO로 변환
//            DiaryLikeDto diaryLikeDto = this.diaryLikeMapper.toDto(diary);
//            Optional<Diary> diary1 = this.diaryRepositoryInterface.findById(diaryLikeDto.getDiaryId());
//            DiaryDto diaryDto = this.diaryMapper.toDto(diary1.get());
            likedDiaries.add(diaryDto);
        }

        return likedDiaries;
    }
    public List<UserDto> findDiaryLikesByDiaryId(Long diaryId) {
        List<DiaryLike> diaryLikes = diaryLikeRepositoryInterface.findByDiary_DiaryId(diaryId);
        List<UserDto> userDtoList = new ArrayList<>();
        for (DiaryLike like : diaryLikes) {
            User user = like.getUser();
            UserDto userDto = new UserDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getImage());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }
}