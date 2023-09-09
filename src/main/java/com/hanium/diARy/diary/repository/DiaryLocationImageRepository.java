package com.hanium.diARy.diary.repository;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanium.diARy.S3Config;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryLocationImageDto;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryLocationImage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriBuilder;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DiaryLocationImageRepository {
    private final DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface;
    private final DiaryLocationInterface diaryLocationRepositoryInterface;
    private final S3Config s3Config;

    public DiaryLocationImageRepository(
            @Autowired DiaryLocationImageRepositoryInterface diaryLocationImageRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationRepositoryInterface,
            @Autowired S3Config s3Config
    ) {
        this.diaryLocationImageRepositoryInterface = diaryLocationImageRepositoryInterface;
        this.diaryLocationRepositoryInterface = diaryLocationRepositoryInterface;
        this.s3Config = s3Config;
    }

    public void createImage(List<DiaryLocationImageDto> diaryLocationImageDtos, Long locationId) throws URISyntaxException, IOException {
        DiaryLocation diaryLocation = diaryLocationRepositoryInterface.findById(locationId).get();
        List<DiaryLocationImage> diaryLocationImages = new ArrayList<>();
        for(DiaryLocationImageDto diaryLocationImageDto : diaryLocationImageDtos) {
            DiaryLocationImage diaryLocationImage = new DiaryLocationImage();
            BeanUtils.copyProperties(diaryLocationImageDto, diaryLocationImage);
            diaryLocationImage.setDiaryLocation(diaryLocation);
            //diaryLocationImage.setImageData(diaryLocationImageDto.getImageData());

            //diaryLocationImage.setImageData(uploadToS3(file, diaryLocationImage.getImageId().toString()));
            System.out.println("locationImageRepository에서 setImageData" + diaryLocationImage.getImageData());
            diaryLocationImageRepositoryInterface.save(diaryLocationImage);
            diaryLocationImages.add(diaryLocationImage);
        }
        diaryLocation.setImages(diaryLocationImages);
    }

    private String uploadToS3(File uploadFile, String filename) {
        s3Config.amazonS3Client().putObject(new PutObjectRequest("diary", filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Config.amazonS3Client().getUrl("diary", filename).toString();
    }


    public List<DiaryLocationImageDto> readImage(DiaryLocation diaryLocation) {
        List<DiaryLocationImageDto> diaryLocationImageDtoList = new ArrayList<>();
        List<DiaryLocationImage> diaryLocationImages = diaryLocationImageRepositoryInterface.findByDiaryLocation(diaryLocation);
        for(DiaryLocationImage diaryLocationImage : diaryLocationImages) {
            DiaryLocationImageDto diaryLocationImageDto = new DiaryLocationImageDto();
            BeanUtils.copyProperties(diaryLocationImage, diaryLocationImageDto);
            diaryLocationImageDto.setDiaryLocationId(diaryLocation.getDiaryLocationId());
            diaryLocationImageDtoList.add(diaryLocationImageDto);
        }

        return diaryLocationImageDtoList;
    }

    public void updateImage(DiaryLocationDto diaryLocationDto) {
        DiaryLocation diaryLocation = diaryLocationRepositoryInterface.findById(diaryLocationDto.getDiaryLocationId()).get();
        List<DiaryLocationImageDto> diaryLocationImageDtoList = diaryLocationDto.getDiaryLocationImageDtoList();
        List<DiaryLocationImage> diaryLocationImages = diaryLocation.getImages();

        // Clear the existing images and update the list with new images
        diaryLocationImages.clear();

        for (DiaryLocationImageDto diaryLocationImageDto : diaryLocationImageDtoList) {
            DiaryLocationImage diaryLocationImage = new DiaryLocationImage();
            BeanUtils.copyProperties(diaryLocationImageDto, diaryLocationImage);
            diaryLocationImage.setDiaryLocation(diaryLocation);
            diaryLocationImages.add(diaryLocationImage);
        }

        diaryLocationRepositoryInterface.save(diaryLocation);
    }



}
