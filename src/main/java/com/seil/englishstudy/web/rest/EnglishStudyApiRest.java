package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.service.StudyDataCreateService;
import com.seil.englishstudy.service.StudyDataDeleteService;
import com.seil.englishstudy.service.StudyDataReadService;
import com.seil.englishstudy.service.StudyDataUpdateService;
import com.seil.englishstudy.web.rest.model.DataCreateRequest;
import com.seil.englishstudy.web.rest.model.DataUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Data."})
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

    @ApiOperation(value = "New Data Create.", notes = "새로운 영어공부 데이터를 저장한다.")
    @PostMapping(produces = "application/json;charset=utf-8")
    public ResponseEntity createData(@RequestBody @Valid DataCreateRequest request) {
        return ResponseEntity.ok(studyDataCreateService.createStudyData(request));
    }

    @ApiOperation(value = "Data Update.", notes = "기존의 데이터를 수정한다.")
    @PutMapping(produces = "application/json;charset=utf-8")
    public ResponseEntity updateData(@RequestBody @Valid DataUpdateRequest request) {
        return ResponseEntity.ok(studyDataUpdateService.updateStudyData(request));
    }

    @ApiOperation(value = "Read all Data.", notes = "모든 데이터를 조회한다.")
    @GetMapping(value="/all")
    public ResponseEntity readDataAll() {
        return ResponseEntity.ok(studyDataReadService.readStudyDataAll());
    }

    @ApiOperation(value = "Read Data by category code.", notes = "항목의 데이터를 조회한다.")
    @GetMapping
    public ResponseEntity readDataByCategory(@RequestParam @Valid long categoryCode) {
        return ResponseEntity.ok(studyDataReadService.readStudyDataByCategory(categoryCode));
    }

    @ApiOperation(value = "Delete Data.", notes = "데이터를 삭제한다.")
    @DeleteMapping
    public ResponseEntity deleteData(@RequestParam @Valid Long id) {
        return ResponseEntity.ok(studyDataDeleteService.deleteStudyData(id));
    }

    @GetMapping(value = "/getTest")
    public ResponseEntity gettest() {

        return ResponseEntity.ok("hihihihi");
    }

    /*
    private final EnglishStudyDataRepository esdrepo;

    public EnglishStudyApiRest (EnglishStudyDataRepository esdrepo) {
        this.esdrepo = esdrepo;
    }

    @GetMapping
    public ResponseEntity createData() {

        EnglishStudyData inputData = EnglishStudyData.builder()
                .question("q")
                .answer("a")
                .categorycode(0)
                .uid("0")
                .build();

        esdrepo.save(inputData);

        return ResponseEntity.ok("saved");
    }

    @GetMapping(value="/all")
    public ResponseEntity readData() {

        List<EnglishStudyData> dataList = esdrepo.findAll();

        return ResponseEntity.ok(dataList);
    }

    @GetMapping(value="/errorTest")
    public ResponseEntity errorTest() {

        throw new StudyDataException(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NOT_VALID, "error test.");
    }*/
}
