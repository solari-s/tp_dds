package com.app.responsablePago;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsablePagoRepository extends JpaRepository<ResponsablePago, String> {
    //los métodos CRUD ya están implementados por JpaRepository
}
