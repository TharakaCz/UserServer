package com.rest.server.repository;

import com.rest.server.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByEmailAndPassword(String email,byte[] password)throws Exception;
    Users findByEmail(String email)throws Exception;
    Users findByVeryfyCode(String veryfyCode)throws Exception;
    /*@Query("delete from users u where u.pid=(:pid)")
    Users deleteUser(@Param("pid")Long pid)throws Exception;*/
}
