package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.CommentMapper;
import com.hanium.diARy.diary.DiaryLikeMapper;
import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class DiaryRepository{
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final TagRepositoryInterface tagRepositoryInterface;
    private final CommentMapper commentMapper;
    private final DiaryLocationInterface diaryLocationRepositoryInterface;
    private final DiaryLocationRepository diaryLocationRepository;
    private final DiaryLikeMapper diaryLikeMapper;
    private final DiaryLocationImageRepository diaryLocationImageRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final AddressRepositoryInterface addressRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    public DiaryRepository(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired CommentMapper commentMapper,
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface,
            @Autowired DiaryLocationRepository diaryLocationRepository,
            @Autowired DiaryLikeMapper diaryLikeMapper,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired AddressRepositoryInterface addressRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface

            ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.commentMapper = commentMapper;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryLocationRepository = diaryLocationRepository;
        this.diaryLikeMapper = diaryLikeMapper;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.addressRepositoryInterface = addressRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Transactional
    public Long createDiary(DiaryRequestDto diaryDto) {
        // 다이어리 작성 dto -> entity
        DiaryDto diaryInfo = diaryDto.getDiaryDto();
        Diary diaryEntity = new Diary();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepositoryInterface.findByEmail(email);
        diaryEntity.setUser(user);
        diaryEntity.setPublic(diaryInfo.isPublic());
        diaryEntity.setTitle(diaryInfo.getTitle());

        diaryEntity.setComments(null);
        diaryEntity.setSatisfaction(diaryInfo.getSatisfaction());
        diaryEntity.setTravelDest(diaryInfo.getTravelDest());
        diaryEntity.setMemo(diaryInfo.getMemo());
        diaryEntity.setTravelStart(diaryInfo.getTravelStart());
        diaryEntity.setTravelEnd(diaryInfo.getTravelEnd());

        //entity 저장
        diaryRepositoryInterface.save(diaryEntity);


        //DiarylocationDto는 따로 떼어서 저장
        List<DiaryLocationDto> diaryLocationDtoList = diaryDto.getDiaryLocationDtoList();

        List<DiaryLocation> savedLocations = new ArrayList<>();
        if (diaryLocationDtoList != null) {
            for (DiaryLocationDto diaryLocationDto : diaryLocationDtoList) {
                DiaryLocation location = new DiaryLocation();
                BeanUtils.copyProperties(diaryLocationDto, location);
                if (addressRepositoryInterface.findByAddress(location.getAddress()) == null) {
                    Address address = new Address();
                    address.setAddress(location.getAddress());
                    addressRepositoryInterface.save(address);
                }

                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeStart()));
                    java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeEnd()));

                    location.setTimeStart(new Time(parsedStartTime.getTime()));
                    location.setTimeEnd(new Time(parsedEndTime.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle parsing exception if needed
                }
                location.setDiary(diaryEntity);

                savedLocations.add(location);
                diaryLocationRepositoryInterface.save(location);

                // DiaryLocation 저장
                List<DiaryLocationImageDto> diaryLocationImageDtoList = diaryLocationDto.getDiaryLocationImageDtoList();
                for(DiaryLocationImageDto diaryLocationImageDto: diaryLocationImageDtoList) {
                    diaryLocationImageRepository.createImage(diaryLocationImageDto, location.getDiaryLocationId());
                }


            }
        }

        for (DiaryTagDto tagDto : diaryDto.getDiaryDto().getTags()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                //없으면 만들고 / 있으면 원래 거 그대로 선택...
                DiaryTag tag = new DiaryTag();
                tag.setName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tagRepositoryInterface.save(tag);
                System.out.println("" + tag);
                diaryEntity.getTags().add(tag);
            }
            else {
                DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tagRepositoryInterface.save(tag);
                diaryEntity.getTags().add(tag);
            }
        }

        // Perform validation if needed (e.g., check for required fields in diaryDto)

        // Save the diaryEntity using the repository
        this.diaryRepositoryInterface.save(diaryEntity);
        return diaryEntity.getDiaryId();
    }

    public Diary readDiary(Long id) {
        Optional<Diary> diaryEntity = this.diaryRepositoryInterface.findById(id);
        if(diaryEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return diaryEntity.get();

    }

    public List<DiaryResponseDto> readPublicDiaryAll() {
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();



        List<Diary> diaryList = this.diaryRepositoryInterface.findByIsPublicTrue();
        for (Diary diary : diaryList){
            DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
            DiaryDto diaryDto = new DiaryDto();
            BeanUtils.copyProperties(diary, diaryDto);
            diaryDto.setComments(this.commentMapper.toDtoList(diary.getComments()));
            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diary.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            diaryDto.setTags(tagDtos);
            diaryDto.setLikes(this.diaryLikeMapper.toDtoList(diary.getDiaryLikes()));
            List<DiaryLocationDto> diaryLocationList = this.diaryLocationRepository.readPublicDiaryLocation(diary.getDiaryId());
            diaryResponseDto.setDiaryDto(diaryDto);
            diaryResponseDto.setDiaryLocationDtoList(diaryLocationList);
            diaryResponseDtoList.add(diaryResponseDto);
        }
        return diaryResponseDtoList;
    }

    public Iterator<Diary> readDiaryAll() {
        return this.diaryRepositoryInterface.findAll().iterator();
    }

    @Transactional
    public void updateDiary(Long id, DiaryRequestDto diaryDto) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Diary diaryEntity = targetEntity.get();
        diaryEntity.setTitle(diaryDto.getDiaryDto().getTitle() == null ? diaryEntity.getTitle() : diaryDto.getDiaryDto().getTitle());

        for (DiaryTag tags : diaryEntity.getTags()) {
            tagRepositoryInterface.findByName(tags.getName()).getDiaries().remove(diaryEntity);
        }
        diaryEntity.getTags().clear();



        for (DiaryTagDto tagDto : diaryDto.getDiaryDto().getTags()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                    //없으면 만들고 / 있으면 원래 거 그대로 선택...
                    DiaryTag tag = new DiaryTag();
                    tag.setName(tagDto.getName());
                    tag.getDiaries().add(diaryEntity);
                    tagRepositoryInterface.save(tag);
                    diaryEntity.getTags().add(tag);
                }
                else {
                    DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                    tag.getDiaries().add(diaryEntity);
                    tagRepositoryInterface.save(tag);
                    diaryEntity.getTags().add(tag);
                }
        }

        diaryEntity.setUser(diaryDto.getDiaryDto().getUser());
        diaryEntity.setPublic(diaryDto.getDiaryDto().isPublic());
        diaryEntity.setMemo(diaryDto.getDiaryDto().getMemo());
        diaryEntity.setTravelDest(diaryDto.getDiaryDto().getTravelDest());
        diaryEntity.setTravelStart(diaryDto.getDiaryDto().getTravelStart());
        diaryEntity.setTravelEnd(diaryDto.getDiaryDto().getTravelEnd());
        diaryEntity.setSatisfaction(diaryDto.getDiaryDto().getSatisfaction() == 0 ? diaryEntity.getSatisfaction() : diaryDto.getDiaryDto().getSatisfaction());

        List<DiaryLocationDto> diaryLocationDtoList = diaryDto.getDiaryLocationDtoList();
        for(DiaryLocationDto diaryLocationDto : diaryLocationDtoList) {
            this.diaryLocationRepository.updateDiaryLocation(diaryLocationDto);
        }

        this.diaryRepositoryInterface.save(diaryEntity);
    }

    @Transactional
    public void deleteDiary(Long id) {
        Optional<Diary> targetEntity = this.diaryRepositoryInterface.findById(id);
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Diary diaryToDelete = targetEntity.get();


        // Remove the diary from associated tags
        for (DiaryTag tag : diaryToDelete.getTags()) {
            tag.getDiaries().remove(diaryToDelete);
        }
        diaryToDelete.getTags().clear();



        List<DiaryLocation> targetDiaryLocation = this.diaryLocationRepositoryInterface.findByDiary_DiaryId(id);
        if (!targetDiaryLocation.isEmpty()) {
            for (DiaryLocation diaryLocation : targetDiaryLocation) {
                this.diaryLocationRepositoryInterface.delete(diaryLocation);
            }
        }

        this.diaryRepositoryInterface.delete(diaryToDelete);

    }


}