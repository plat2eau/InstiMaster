package com.instimaster.dao;

import com.instimaster.model.institute.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteDao extends JpaRepository<Institute, String> {
}
