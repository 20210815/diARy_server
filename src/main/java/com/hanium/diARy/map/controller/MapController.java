package com.hanium.diARy.map.controller;

import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.map.dto.MapAllDiaryDto;
import com.hanium.diARy.map.dto.MapDiaryDto;
import com.hanium.diARy.map.service.MapService;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;
    private final UserRepositoryInterface userRepositoryInterface;

    public MapController(
            @Autowired MapService mapService,
            @Autowired UserRepositoryInterface userRepositoryInterface
    ) {
        this.mapService = mapService;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    //지도에서 같은 장소에 다른 사람들의 일기 표현

    //로그인을 해서 현재 내 일기가 먼저 나오도록 수정 필요
    @GetMapping("/{address}")
    public List<MapDiaryDto> readAllDiaryByAddress(@PathVariable("address") String address) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //null일 때도 추가로 만들어야 함
            String email = authentication.getName();
            User user = userRepositoryInterface.findByEmail(email);
            return this.mapService.readAllDiaryByAddress(address, user);
        }
        else {
            return this.mapService.readAllDiaryByAddress(address, null);
        }

    }

    @GetMapping("")
    public List<MapAllDiaryDto> readAllDiaryByAddressList() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //null일 때도 추가로 만들어야 함
            String email = authentication.getName();
            User user = userRepositoryInterface.findByEmail(email);
            return this.mapService.readAllDiary(user);
        }
        else {
            return this.mapService.readAllDiary(null);
        }
    }
}
