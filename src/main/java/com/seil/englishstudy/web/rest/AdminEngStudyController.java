package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.dto.request.AdminEngStudyUpdateRequest;
import com.seil.englishstudy.dto.response.AdminEngStudyUpdateResponse;
import com.seil.englishstudy.dto.request.AdminEngStudyCreateRequest;
import com.seil.englishstudy.service.admin.AdminEngStudyCreateService;
import com.seil.englishstudy.service.admin.AdminEngStudyDeleteService;
import com.seil.englishstudy.service.admin.AdminEngStudyReadService;
import com.seil.englishstudy.service.admin.AdminEngStudyUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "/admin/studydatas")
@RestController
public class AdminEngStudyController {

    private final AdminEngStudyCreateService adminEngStudyCreateService;
    private final AdminEngStudyReadService adminEngStudyReadService;
    private final AdminEngStudyUpdateService adminEngStudyUpdateService;
    private final AdminEngStudyDeleteService adminEngStudyDeleteService;

    @PostMapping
    public ResponseEntity createEngStudyData(@RequestBody @Valid final AdminEngStudyCreateRequest adminEngStudyCreateRequest) {
        Long engStudyDataId = adminEngStudyCreateService.createEngStudyData(adminEngStudyCreateRequest);
        return ResponseEntity.created(URI.create("/admin/studydatas/" + engStudyDataId)).build();
    }

    @GetMapping(value = "/{engStudyDataId}")
    public ResponseEntity readEngStudyDataById(@PathVariable final Long engStudyDataId) {
        return ResponseEntity.ok(adminEngStudyReadService.readEngStudyById(engStudyDataId));
    }

    @PutMapping(value = "/{engStudyDataId}")
    public ResponseEntity updateEngStudyData(@PathVariable final Long engStudyDataId, @RequestBody @Valid final AdminEngStudyUpdateRequest adminEngStudyUpdateRequest) {

        AdminEngStudyUpdateResponse adminEngStudyUpdateResponse = adminEngStudyUpdateService.updateEngStudyData(engStudyDataId, adminEngStudyUpdateRequest);

        if (adminEngStudyUpdateRequest.getCategoryCode() == adminEngStudyUpdateResponse.getCategoryCode()
            && adminEngStudyUpdateRequest.getQuestion().equals(adminEngStudyUpdateResponse.getQuestion())
            && adminEngStudyUpdateRequest.getAnswer().equals(adminEngStudyUpdateResponse.getAnswer()))
            return ResponseEntity.noContent().build();

        return ResponseEntity.created(URI.create("/admin/studydatas/" + engStudyDataId)).build();
    }

    @DeleteMapping(value = "/{engStudyDataId}")
    public ResponseEntity deleteEngStudyData(@PathVariable final Long engStudyDataId) {
        adminEngStudyDeleteService.deleteEngStudyData(engStudyDataId);
        return ResponseEntity.noContent().build();
    }

}
