package com.instimaster.controller;

import com.instimaster.model.institute.Institute;
import com.instimaster.service.InstituteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstituteController {
    private static final String INSTITUTES_PATH = "/institutes";

    private final InstituteService instituteService;

    @Autowired
    public InstituteController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @GetMapping(INSTITUTES_PATH)
    public ResponseEntity<List<Institute>> getAllInstitutes() {
        return instituteService.getAllInstitutes();
    }

    @GetMapping(INSTITUTES_PATH + "/{id}")
    public ResponseEntity<Institute> getInstitute(@PathVariable String id) {
        return instituteService.getInstituteById(id);
    }

    @PostMapping(INSTITUTES_PATH)
    public ResponseEntity<String> createInstitute(@RequestBody @Valid Institute instituteParam) {
        return instituteService.createInstitute(instituteParam);
    }

    @PutMapping(INSTITUTES_PATH)
    public ResponseEntity<String> updateInstitute(@RequestBody @Valid Institute instituteParam) {
        return instituteService.updateInstitute(instituteParam);
    }

    @DeleteMapping(INSTITUTES_PATH + "/{id}")
    public ResponseEntity deleteInstitute(@PathVariable String id) {
        return instituteService.deleteInstitute(id);
    }
}
