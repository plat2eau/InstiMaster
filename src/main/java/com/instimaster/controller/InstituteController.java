package com.instimaster.controller;

import com.instimaster.dao.InstituteDao;
import com.instimaster.model.request.CreateInstituteRequest;
import com.instimaster.model.request.UpdateInstituteRequest;
import com.instimaster.model.response.CreateInstituteResponse;
import com.instimaster.model.response.GetAllInstitutesResponse;
import com.instimaster.model.response.GetInstituteByIdResponse;
import com.instimaster.model.response.UpdateInstituteResponse;
import com.instimaster.service.InstituteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstituteController {
    private static final String INSTITUTES_PATH = "/institutes";

    private final InstituteService instituteService;

    @GetMapping(INSTITUTES_PATH)
    public ResponseEntity<GetAllInstitutesResponse> getAllInstitutes() {
        return instituteService.getAllInstitutes();
    }

    @GetMapping(INSTITUTES_PATH + "/{id}")
    public ResponseEntity<GetInstituteByIdResponse> getInstituteById(@PathVariable String id) {
        return instituteService.getInstituteById(id);
    }

    @PostMapping(INSTITUTES_PATH)
    public ResponseEntity<CreateInstituteResponse> createInstitute(@RequestBody @Valid CreateInstituteRequest req) {
        return instituteService.createInstitute(req);
    }

    @PutMapping(INSTITUTES_PATH)
    public ResponseEntity<UpdateInstituteResponse> updateInstitute(@RequestBody @Valid UpdateInstituteRequest req) {
        return instituteService.updateInstitute(req);
    }

    @DeleteMapping(INSTITUTES_PATH + "/{id}")
    public ResponseEntity deleteInstitute(@PathVariable String id) {
        return instituteService.deleteInstitute(id);
    }
}
