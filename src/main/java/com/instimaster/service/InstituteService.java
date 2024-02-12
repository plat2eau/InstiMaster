package com.instimaster.service;

import com.instimaster.dao.InstituteDao;
import com.instimaster.entity.Institute;
import com.instimaster.model.request.CreateInstituteRequest;
import com.instimaster.model.request.UpdateInstituteRequest;
import com.instimaster.model.response.CreateInstituteResponse;
import com.instimaster.model.response.GetAllInstitutesResponse;
import com.instimaster.model.response.GetInstituteByIdResponse;
import com.instimaster.model.response.UpdateInstituteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstituteService {

    private final InstituteDao instituteDao;

    public ResponseEntity<GetAllInstitutesResponse> getAllInstitutes() {
        return new ResponseEntity<>(new GetAllInstitutesResponse(instituteDao.findAll()), HttpStatus.OK);
    }

    public ResponseEntity<GetInstituteByIdResponse> getInstituteById(String id) {
        Optional<Institute> institute = null;
        institute = instituteDao.findById(id);

        if(institute.isPresent()) {
            return new ResponseEntity<>(new GetInstituteByIdResponse(institute.get()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Institute with id: " + id + " not found");
        }
    }

    public ResponseEntity<CreateInstituteResponse> createInstitute(CreateInstituteRequest req) {
        Institute institute = new Institute();
        BeanUtils.copyProperties(req, institute);
        instituteDao.save(institute);
        return new ResponseEntity<>(new CreateInstituteResponse(institute.getId()), HttpStatus.OK);
    }

    public ResponseEntity<UpdateInstituteResponse> updateInstitute(UpdateInstituteRequest req) {
        Institute instituteToBeInserted;
        Optional<Institute> existingInstitute = instituteDao.findById(req.getId());

        if(existingInstitute.isPresent()) {
            // Loading institute from database
            instituteToBeInserted = new Institute();
            BeanUtils.copyProperties(existingInstitute, instituteToBeInserted);
            // Overwriting new values to be updated
            BeanUtils.copyProperties(req, instituteToBeInserted);
            instituteDao.save(instituteToBeInserted);
            return new ResponseEntity<>(new UpdateInstituteResponse(instituteToBeInserted.getId()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Institute with id: " + req.getId() + " not found");
        }
    }

    public ResponseEntity deleteInstitute(String id) {
        instituteDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
