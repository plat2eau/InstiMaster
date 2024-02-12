package com.instimaster.dao;

import com.instimaster.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteDao extends JpaRepository<Institute, String> {
}
