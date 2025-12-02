package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.huesped.Huesped;
import com.app.huesped.HuespedPK;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, HuespedPK>, JpaSpecificationExecutor<Huesped> {}
