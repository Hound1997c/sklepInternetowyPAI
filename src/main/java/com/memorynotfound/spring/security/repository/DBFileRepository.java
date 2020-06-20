package com.memorynotfound.spring.security.repository;

import com.memorynotfound.spring.security.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}