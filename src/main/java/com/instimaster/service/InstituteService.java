package com.instimaster.service;

import com.instimaster.dao.InstituteDao;
import com.instimaster.exception.NoSuchInstitutionException;
import com.instimaster.model.institute.Institute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstituteService {

    private final InstituteDao instituteDao;

    @Autowired
    public InstituteService(InstituteDao instituteDao) {
        this.instituteDao = instituteDao;
    }

    public ResponseEntity<List<Institute>> getAllInstitutes() {
        return new ResponseEntity<>(instituteDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Institute> getInstituteById(String instituteId) {
        Optional<Institute> institute = null;
        institute = instituteDao.findById(instituteId);

        if(institute.isPresent()) {
            return new ResponseEntity<>(institute.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Institute with id: " + instituteId + " not found");
        }
    }

    public ResponseEntity<String> createInstitute(Institute instituteParam) {
        Institute instituteWithId = new Institute(instituteParam);
        instituteDao.save(instituteWithId);
        return new ResponseEntity<>(instituteWithId.getId(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateInstitute(Institute instituteParam) {
        Institute instituteToBeInserted;
        Optional<Institute> existingInstitute = instituteDao.findById(instituteParam.getId());

        if(existingInstitute.isPresent()) {
            // Loading institute from database
            instituteToBeInserted = new Institute(existingInstitute.get());
            // Overwriting new values to be updated
            BeanUtils.copyProperties(instituteToBeInserted, instituteParam);
            instituteDao.save(instituteToBeInserted);
            return new ResponseEntity<>(instituteToBeInserted.getId(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Institute with id: " + instituteParam.getId() + " not found");
        }
    }

    public ResponseEntity deleteInstitute(String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
