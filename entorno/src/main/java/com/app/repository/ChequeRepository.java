package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;
import com.app.cheque.Cheque;
import org.springframework.stereotype.Repository;

@Repository

public interface ChequeRepository extends JpaRepository<Cheque,Integer> {}
