package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.service.*;
import com.seil.englishstudy.web.rest.model.DataCreateRequest;
import com.seil.englishstudy.web.rest.model.DataUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Manage English Study Data API."})
@RestController
@RequestMapping(value="/studydata")
public class EnglishStudyApiRest {

    private StudyDataCreateService studyDataCreateService;
    private StudyDataUpdateService studyDataUpdateService;
    private StudyDataReadService studyDataReadService;
    private StudyDataDeleteService studyDataDeleteService;

    public EnglishStudyApiRest(StudyDataCreateService studyDataCreateService,
                               StudyDataUpdateService studyDataUpdateService,
                               StudyDataReadService studyDataReadService,
                               StudyDataDeleteService studyDataDeleteService) {
        this.studyDataCreateService = studyDataCreateService;
        this.studyDataUpdateService = studyDataUpdateService;
        this.studyDataReadService = studyDataReadService;
        this.studyDataDeleteService = studyDataDeleteService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "New Data Create.", notes = "새로운 영어공부 데이터를 저장한다.")
    @PostMapping(value="/admin/create", produces = "application/json;charset=utf-8")
    public ResponseEntity createData(@RequestBody @Valid DataCreateRequest request) {
        return ResponseEntity.ok(studyDataCreateService.createStudyData(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Get PK.", notes = "카테고리 코드, 한글문장, 영어문장으로 pk값을 가져온다.")
    @GetMapping(value="/pk")
    public ResponseEntity readPK(@RequestParam @Valid long categoryCode,
                                 @RequestParam @Valid String question ,
                                 @RequestParam @Valid String answer) {
        return ResponseEntity.ok(studyDataReadService.readPK(categoryCode, question, answer));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Data Update.", notes = "기존의 데이터를 수정한다.")
    @PutMapping(value="admin/update", produces = "application/json;charset=utf-8")
    public ResponseEntity updateData(@RequestBody @Valid DataUpdateRequest request) {
        return ResponseEntity.ok(studyDataUpdateService.updateStudyData(request));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Read all Data.", notes = "모든 데이터를 조회한다.")
    @GetMapping(value="/all")
    public ResponseEntity readDataAll() {
        return ResponseEntity.ok(studyDataReadService.readStudyDataAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Read Data by category code.", notes = "항목의 데이터를 조회한다.")
    @GetMapping(value="/byCategory")
    public ResponseEntity readDataByCategory(@RequestParam @Valid long categoryCode) {
        return ResponseEntity.ok(studyDataReadService.readStudyDataByCategory(categoryCode));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Read Data by Ids", notes = "id 리스트에 해당하는 데이터 리스트를 조회한다.")
    @GetMapping(value="/byIds")
    public ResponseEntity readDataByIds(@RequestParam @Valid List<Long> ids) {
        return ResponseEntity.ok(studyDataReadService.readStudyDataByIds(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Delete Data.", notes = "데이터를 삭제한다.")
    @DeleteMapping(value="/admin/delete")
    public ResponseEntity deleteData(@RequestParam @Valid Long id) {
        return ResponseEntity.ok(studyDataDeleteService.deleteStudyData(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token after sign in", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Delete All Data.", notes = "데이터를 전부 삭제한다.")
    @DeleteMapping(value="admin/delete/all")
    public ResponseEntity deleteDataAll() {
        return ResponseEntity.ok(studyDataDeleteService.deleteStudyDataAll());
    }

}
