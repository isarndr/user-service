package com.codewithisa.userservice.repository;

import com.codewithisa.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from users where user_id = :userId"
    )
    Users findUserByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from users where user_id = :userId"
    )
    void deleteUserByUserId(@Param("userId") Long userId);

    @Query(
            nativeQuery = true,
            value = "select * from users where username=:username"
    )
    Users getUserByUsername(@Param("username") String username);
}

