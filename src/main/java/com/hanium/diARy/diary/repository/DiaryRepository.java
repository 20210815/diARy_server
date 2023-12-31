package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.dto.*;
import com.hanium.diARy.diary.entity.*;
import com.hanium.diARy.diary.service.ClovaService;
import com.hanium.diARy.plan.dto.PlanLocationDto;
import com.hanium.diARy.plan.dto.PlanResponseDto;
import com.hanium.diARy.plan.entity.PlanLocation;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class DiaryRepository{
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final TagRepositoryInterface tagRepositoryInterface;
    private final DiaryLocationInterface diaryLocationRepositoryInterface;
    private final DiaryLocationRepository diaryLocationRepository;
    private final DiaryLocationImageRepository diaryLocationImageRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final AddressRepositoryInterface addressRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final ClovaService clovaService;
    private final CommentRepository commentRepository;
    private final AddressRepository addressRepository;
    private final DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface;
    private final TagRepository tagRepository;
    public DiaryRepository(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired TagRepositoryInterface tagRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface,
            @Autowired DiaryLocationRepository diaryLocationRepository,
            @Autowired DiaryLocationImageRepository diaryLocationImageRepository,
            @Autowired DiaryLikeRepository diaryLikeRepository,
            @Autowired AddressRepositoryInterface addressRepositoryInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface,
            @Autowired ClovaService clovaService,
            @Autowired CommentRepository commentRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface,
            @Autowired TagRepository tagRepository

            ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.tagRepositoryInterface = tagRepositoryInterface;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.diaryLocationRepository = diaryLocationRepository;
        this.diaryLocationImageRepository = diaryLocationImageRepository;
        this.diaryLikeRepository = diaryLikeRepository;
        this.addressRepositoryInterface = addressRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.clovaService = clovaService;
        this.commentRepository = commentRepository;
        this.addressRepository = addressRepository;
        this.diaryLocationImageRepositoryInterface = diaryLocationImageRepositoryInterface;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Long createDiary(DiaryRequestDto diaryDto) throws URISyntaxException, IOException {
        System.out.println(diaryDto.getDiaryLocationDtoList());
        // 다이어리 작성 dto -> entity
        DiaryDto diaryInfo = diaryDto.getDiaryDto();
        Diary diaryEntity = new Diary();
        //User user = new User();
        //user.setUserId(1L);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepositoryInterface.findByEmail(email);
        diaryEntity.setUser(user);
        diaryEntity.setPublic(diaryInfo.isPublic());
        diaryEntity.setTitle(diaryInfo.getTitle());
        diaryEntity.setTravelDest(diaryInfo.getTravelDest());
        diaryEntity.setMemo(diaryInfo.getMemo());
        diaryEntity.setTravelStart(diaryInfo.getTravelStart());
        diaryEntity.setTravelEnd(diaryInfo.getTravelEnd());
        BeanUtils.copyProperties(diaryInfo, diaryEntity);

        //entity 저장
        diaryRepositoryInterface.save(diaryEntity);

        System.out.println("createDiary" + diaryDto.getDiaryLocationDtoList());
        
        
        //DiarylocationDto는 따로 떼어서 저장
        List<DiaryLocationDto> diaryLocationDtoList = diaryDto.getDiaryLocationDtoList();
        int score = 0;
        List<DiaryLocation> savedLocations = new ArrayList<>();
        if (diaryLocationDtoList != null) {
            for (DiaryLocationDto diaryLocationDto : diaryLocationDtoList) {
                DiaryLocation location = new DiaryLocation();
                Double positive = 0.0;

                //저장을 해야 set이 될 듯
                BeanUtils.copyProperties(diaryLocationDto, location);
                location.setDiary(diaryEntity);
                diaryLocationRepositoryInterface.save(location);


                //null이거나 빈칸이면 그냥 50이도록
                if((Objects.equals(diaryLocationDto.getContent(), ""))||(diaryLocationDto.getContent() == null)) {
                    location.setContent("");
                    positive = 50.0;
                    location.setLocationSatisfaction((int) Math.round(positive));

                }
                else {
                    positive = this.clovaService.performSentimentAnalysis(diaryLocationDto.getContent());
                    location.setLocationSatisfaction((int) Math.round(positive));
                }
                score += positive.intValue();
                //일기에 추가할 것
                savedLocations.add(location);
                diaryLocationRepositoryInterface.save(location);

                System.out.println(positive);
                System.out.println("createdDiary - location 생성 결과 " + location);



                //address 있는지 확인 후에 없으면 새로운 객체 생성
                if(!(addressRepositoryInterface.existsByXAndY(diaryLocationDto.getX(), diaryLocationDto.getY()))) {
                    Address address = new Address();
                    address.setX(diaryLocationDto.getX());
                    address.setY(diaryLocationDto.getY());
                    address.setAddress(diaryLocationDto.getAddress());
                    addressRepositoryInterface.save(address);
                }
                List<DiaryLocationImageDto> diaryLocationImageDtoList = diaryLocationDto.getDiaryLocationImageDtoList();
                if(diaryLocationImageDtoList != null) {
                    // DiaryLocationImage 저장
                    diaryLocationImageRepository.createImage(diaryLocationImageDtoList, location.getDiaryLocationId());
                }

            }
            diaryEntity.setDiaryLocations(savedLocations);

            try {
                if (savedLocations.isEmpty()) {
                    throw new ArithmeticException("Dividing by zero");
                }
                score /= savedLocations.size();
            } catch (ArithmeticException e) {
                // 0으로 나누는 오류가 발생한 경우에 대한 예외 처리 코드
                System.err.println("Error: " + e.getMessage());
                // 이 부분에 오류 처리 또는 메시지 출력에 필요한 로직을 추가하세요.
                score = 0;
            }

            System.out.println(score);
            //저장 후에 여기서 만족도 저장
            diaryEntity.setSatisfaction(score);
        }

        for (DiaryTagDto tagDto : diaryDto.getDiaryDto().getTags()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                //없으면 만들고 / 있으면 원래 거 그대로 선택...
                DiaryTag tag = new DiaryTag();
                tag.setName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tag.setNumber(tag.getDiaries().size());
                tagRepositoryInterface.save(tag);
                System.out.println("" + tag);
                diaryEntity.getTags().add(tag);
            }
            else {
                DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                tag.getDiaries().add(diaryEntity);
                tag.setNumber(tag.getDiaries().size());
                tagRepositoryInterface.save(tag);
                diaryEntity.getTags().add(tag);
            }
        }

        for (DiaryLikeDto diaryLikeDto : diaryDto.getDiaryDto().getLikes()) {
            //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
            diaryLikeRepository.createDiaryLike(diaryLikeDto.getDiaryId());
        }

        // Perform validation if needed (e.g., check for required fields in diaryDto)

        // Save the diaryEntity using the repository
        this.diaryRepositoryInterface.save(diaryEntity);
        tagRepository.DescDiaryTag();
        return diaryEntity.getDiaryId();
    }

    public DiaryResponseDto readDiary(Long id) {
        DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
        Diary diaryEntity = this.diaryRepositoryInterface.findById(id).get();
        DiaryDto dto = new DiaryDto();
        BeanUtils.copyProperties(diaryEntity, dto);
        dto.setComments(commentRepository.readDiaryCommentAll(diaryEntity.getDiaryId()));
        dto.setLikes(this.diaryLikeRepository.readDiaryLike(diaryEntity.getDiaryId()));
        List<DiaryTagDto> tagDtos = new ArrayList<>();
        for (DiaryTag tag : diaryEntity.getTags()) {
            DiaryTagDto tagDto = new DiaryTagDto();
            BeanUtils.copyProperties(tag, tagDto);
            tagDtos.add(tagDto);
        }
        dto.setTags(tagDtos);
        System.out.println(dto.getLikes());
        dto.setTravelStart(diaryEntity.getTravelStart());
        dto.setTravelEnd(diaryEntity.getTravelEnd());


        List<DiaryLocationDto> diaryLocationDtoList = this.diaryLocationRepository.readPublicDiaryLocation(diaryEntity.getDiaryId());
        diaryResponseDto.setDiaryDto(dto);
        diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);

        UserDto userDto = new UserDto();
        User user = userRepositoryInterface.findById(diaryEntity.getUser().getUserId()).get();
        BeanUtils.copyProperties(user, userDto);
        diaryResponseDto.setUserDto(userDto);
        System.out.println("diaryRepository" + diaryResponseDto);
        return diaryResponseDto;
    }

    public List<DiaryResponseDto> readPublicDiaryAll() {
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();



        List<Diary> diaryList = this.diaryRepositoryInterface.findByIsPublicTrue();
        for (Diary diary : diaryList){
            DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
            DiaryDto diaryDto = new DiaryDto();
            BeanUtils.copyProperties(diary, diaryDto);
            diaryDto.setComments(commentRepository.readDiaryCommentAll(diary.getDiaryId()));
            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diary.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            diaryDto.setTags(tagDtos);
            diaryDto.setLikes(this.diaryLikeRepository.readDiaryLike(diary.getDiaryId()));
            List<DiaryLocationDto> diaryLocationList = this.diaryLocationRepository.readPublicDiaryLocation(diary.getDiaryId());
            diaryResponseDto.setDiaryDto(diaryDto);
            diaryResponseDto.setDiaryLocationDtoList(diaryLocationList);


            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(diary.getUser().getUserId()).get();
            BeanUtils.copyProperties(user, userDto);
            diaryResponseDto.setUserDto(userDto);
            diaryResponseDtoList.add(diaryResponseDto);


        }
        return diaryResponseDtoList;
    }

    public Iterator<Diary> readDiaryAll() {
        return this.diaryRepositoryInterface.findAll().iterator();
    }

    @Transactional
    public void updateDiary(Long id, DiaryRequestDto requestDto) throws URISyntaxException, IOException {
        System.out.println("수정!!!" + requestDto.getDiaryLocationDtoList());
        Diary diaryEntity = this.diaryRepositoryInterface.findById(id).get();

        if (requestDto.getDiaryDto() != null) {
            if(requestDto.getDiaryDto().getImageData() != null) {
                diaryEntity.setImageData(requestDto.getDiaryDto().getImageData());
                diaryEntity.setImageUri(requestDto.getDiaryDto().getImageUri());
            }

            if (requestDto.getDiaryDto().getTags() == null) {
                diaryEntity.getTags().clear();
            }
            else {
                for (DiaryTag tags : diaryEntity.getTags()) {
                    tagRepositoryInterface.findByName(tags.getName()).getDiaries().remove(diaryEntity);
                }
                diaryEntity.getTags().clear();
                for (DiaryTagDto tagDto : requestDto.getDiaryDto().getTags()) {
                    //받아온 태그를 하나하나 떼어서 원래 있는 건지 비교해봐야 함
                    if(tagRepositoryInterface.findByName(tagDto.getName()) == null) {
                        //없으면 만들고 / 있으면 원래 거 그대로 선택...
                        DiaryTag tag = new DiaryTag();
                        tag.setName(tagDto.getName());
                        tag.getDiaries().add(diaryEntity);
                        tag.setNumber(tag.getDiaries().size());
                        tagRepositoryInterface.save(tag);
                        diaryEntity.getTags().add(tag);
                    }
                    else {
                        DiaryTag tag = tagRepositoryInterface.findByName(tagDto.getName());
                        tag.getDiaries().add(diaryEntity);
                        tag.setNumber(tag.getDiaries().size());
                        tagRepositoryInterface.save(tag);
                        diaryEntity.getTags().add(tag);
                    }
                }
            }

            DiaryDto diaryDto = requestDto.getDiaryDto();
            if(diaryDto != null) {
                if(diaryDto.getMemo() != null) {
                    diaryEntity.setMemo(diaryDto.getMemo());
                }
                if(diaryDto.isPublic() != diaryEntity.isPublic()) {
                    diaryEntity.setPublic(diaryDto.isPublic());
                }
                if(diaryDto.getTitle() != diaryEntity.getTitle()) {
                    diaryEntity.setTitle(diaryDto.getTitle());
                }
                if(diaryDto.getTravelDest() != diaryEntity.getTravelDest()) {
                    diaryEntity.setTravelDest(diaryDto.getTravelDest());
                }
                if (diaryDto.getTravelStart() != null) {
                    diaryEntity.setTravelStart(diaryDto.getTravelStart());
                }
                if (diaryDto.getTravelEnd() != null) {
                    diaryEntity.setTravelEnd(diaryDto.getTravelEnd());
                }
            }
        }
        System.out.println("여기까지 들어옴");
        int score = 0;
        List<DiaryLocationDto> diaryLocationDtoList = requestDto.getDiaryLocationDtoList();
        diaryEntity.getDiaryLocations().clear();
        if (diaryLocationDtoList != null) {
            
            //저장할 것
            List<DiaryLocation> savedLocations = new ArrayList<>();
            
            for (DiaryLocationDto diaryLocationDto : diaryLocationDtoList) {
                
                DiaryLocation diaryLocation = new DiaryLocation();
                Double positive = 0.0;
                BeanUtils.copyProperties(diaryLocationDto, diaryLocation);
                diaryLocation.setDiary(diaryEntity);
                diaryLocationRepositoryInterface.save(diaryLocation);
                System.out.println("일단 저장");
                
                //null이거나 빈칸이면 그냥 50이도록
                if((Objects.equals(diaryLocationDto.getContent(), ""))||(diaryLocationDto.getContent() == null)) {
                    diaryLocation.setContent("");
                    positive = 50.0;
                    diaryLocation.setLocationSatisfaction((int) Math.round(positive));
                }
                else {
                    positive = this.clovaService.performSentimentAnalysis(diaryLocationDto.getContent());
                    diaryLocation.setLocationSatisfaction((int) Math.round(positive));
                }
                
                //감정분석 평균으로 diaryEntity에 만족도 저장 하나하나씩 저장
                score += positive.intValue();
                System.out.println("score" + score);

                if (!(addressRepositoryInterface.existsByXAndY(diaryLocationDto.getX(), diaryLocation.getY()))) {
                    Address address = new Address();
                    address.setX(diaryLocationDto.getX());
                    address.setY(diaryLocationDto.getY());
                    address.setAddress(diaryLocationDto.getAddress());
                    addressRepositoryInterface.save(address);
                    addressRepository.deleteAddress();
                }
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeStart()));
                    java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeEnd()));

                    diaryLocation.setTimeStart(new Time(parsedStartTime.getTime()));
                    diaryLocation.setTimeEnd(new Time(parsedEndTime.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                diaryLocationRepositoryInterface.save(diaryLocation);
                System.out.println("이미지 저장하기");
                List<DiaryLocationImageDto> diaryLocationImageDtoList = diaryLocationDto.getDiaryLocationImageDtoList();
                if((diaryLocationImageDtoList != null)) {
                    // DiaryLocationImage 저장
                    diaryLocationImageRepository.createImage(diaryLocationImageDtoList, diaryLocation.getDiaryLocationId());
                }
                System.out.println("저장 완료");

                diaryLocationRepositoryInterface.save(diaryLocation);
                savedLocations.add(diaryLocation);
            }

            try {
                if (savedLocations.isEmpty()) {
                    throw new ArithmeticException("Dividing by zero");
                }
                score /= savedLocations.size();
            } catch (ArithmeticException e) {
                // 0으로 나누는 오류가 발생한 경우에 대한 예외 처리 코드
                System.err.println("Error: " + e.getMessage());
                // 이 부분에 오류 처리 또는 메시지 출력에 필요한 로직을 추가하세요.
                score = 0;
            }
            diaryEntity.setSatisfaction(score);
            System.out.println("가진 location" + diaryEntity.getDiaryLocations());
        }

//        DiaryDto updatedDiaryDto = new DiaryDto();
//        BeanUtils.copyProperties(diaryEntity, updatedDiaryDto);
//
//        List<DiaryLocationDto> updatedDiaryLocationDtos = new ArrayList<>();
//        for(DiaryLocation location : diaryEntity.getDiaryLocations()) {
//            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
//            BeanUtils.copyProperties(location, diaryLocationDto);
//            updatedDiaryLocationDtos.add(diaryLocationDto);
//        }
//
//

//        List<DiaryLocationDto> diaryLocationDtoList = diaryDto.getDiaryLocationDtoList();
//
//        if(diaryLocationDtoList != null) {
//            // null이 아니면 이전에 달려있던 거 클리어 함
//            diaryEntity.getDiaryLocations().clear();
//            // 저장할 로케이션
//            List<DiaryLocation> savedLocations = new ArrayList<>();
//
//            //받아온 로케이션을 새롭게 만들기
//            for(DiaryLocationDto diaryLocationDto : diaryLocationDtoList) {
//                DiaryLocation location = new DiaryLocation();
//                BeanUtils.copyProperties(diaryLocationDto, location);
//                try {
//                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//                    java.util.Date parsedStartTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeStart()));
//                    java.util.Date parsedEndTime = timeFormat.parse(String.valueOf(diaryLocationDto.getTimeEnd()));
//
//                    location.setTimeStart(new Time(parsedStartTime.getTime()));
//                    location.setTimeEnd(new Time(parsedEndTime.getTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                location.setDiary(diaryEntity);
//                //다이어리 설정
//                savedLocations.add(location);
//                diaryLocationRepositoryInterface.save(location);
//            }
//            diaryEntity.setDiaryLocations(savedLocations);
//        }

//        List<DiaryLocationDto> updatedDiaryLocationDtos = new ArrayList<>();
//        for (DiaryLocation location : diaryEntity.getDiaryLocations()) {
//            DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
//            BeanUtils.copyProperties(location, diaryLocationDto);
//            updatedDiaryLocationDtos.add(diaryLocationDto);
//        }

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