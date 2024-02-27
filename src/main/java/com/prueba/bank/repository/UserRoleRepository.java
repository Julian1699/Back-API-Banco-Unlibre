package com.prueba.bank.repository;

import com.prueba.bank.domain.UserRole;
import com.prueba.bank.util.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {

}