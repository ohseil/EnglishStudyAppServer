package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.service.study.EngStudyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/studydatas")
@RestController
public class EngStudyController {

    private final EngStudyPageService engStudyPageService;

    @GetMapping
    public ResponseEntity getEngStudyPage(@RequestParam final long categoryCode) {
        return ResponseEntity.ok(engStudyPageService.getEngStudyPage(categoryCode));
    }
}
