package com.instimaster.service;

import com.instimaster.dao.InstituteDao;
import com.instimaster.entity.Institute;
import com.instimaster.exceptions.institute.InstituteNotFoundException;
import com.instimaster.model.request.CreateInstituteRequest;
import com.instimaster.model.request.UpdateInstituteRequest;
import com.instimaster.model.response.CreateInstituteResponse;
import com.instimaster.model.response.GetAllInstitutesResponse;
import com.instimaster.model.response.GetInstituteByIdResponse;
import com.instimaster.model.response.UpdateInstituteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstituteServiceTest {

    @InjectMocks
    InstituteService instituteService;

    @Mock
    InstituteDao instituteDao;
    @Mock
    List<Institute> allInstitutes;
    @Mock
    Institute institute;
    @Mock
    CreateInstituteRequest createInstituteRequest;
    @Mock
    UpdateInstituteRequest updateInstituteRequest;

    @Test
    void getAllInstitutes() {
        when(instituteDao.findAll()).thenReturn(allInstitutes);
        ResponseEntity<GetAllInstitutesResponse> response = instituteService.getAllInstitutes();
        verify(instituteDao, times(1)).findAll();
        assertEquals(Objects.requireNonNull(response.getBody()).getInstitutes(), allInstitutes);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getInstituteById() {
        when(instituteDao.findById(any())).thenReturn(Optional.ofNullable(institute));
        ResponseEntity<GetInstituteByIdResponse> response = instituteService.getInstituteById("id");
        verify(instituteDao, times(1)).findById(any());
        assertEquals(Objects.requireNonNull(response.getBody()).getInstitute(), institute);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getInstituteByIdError() {
        when(instituteDao.findById(any())).thenReturn(Optional.empty());
        assertThrows(InstituteNotFoundException.class, () -> instituteService.getInstituteById("id"));
        verify(instituteDao, times(1)).findById(any());
    }

    @Test
    void createInstitute() {
        ResponseEntity<CreateInstituteResponse> response = instituteService.createInstitute(createInstituteRequest);
        verify(instituteDao, times(1)).save(any());
        assertFalse(Objects.requireNonNull(response.getBody()).getId().isEmpty());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateInstitute() {
        when(instituteDao.findById(any())).thenReturn(Optional.ofNullable(institute));
        ResponseEntity<UpdateInstituteResponse> response = instituteService.updateInstitute(updateInstituteRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(instituteDao, times(1)).save(any());
    }

    @Test
    void updateInstituteInstituteNotFound() {
        when(instituteDao.findById(any())).thenReturn(Optional.empty());
        assertThrows(InstituteNotFoundException.class, () -> instituteService.updateInstitute(updateInstituteRequest));
        verify(instituteDao, times(1)).findById(any());
    }

    @Test
    void deleteInstitute() {
        ResponseEntity response = instituteService.deleteInstitute("id");
        verify(instituteDao, times(1)).deleteById(any());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}