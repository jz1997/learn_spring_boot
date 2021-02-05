package com.example.spring.boot.security.jwt.demo.repository;

import com.example.spring.boot.security.jwt.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository:
 *
 * @author jzheng
 * @since 2021/1/1 12:01 上午
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据 username 查询对应的对象
     * @param username username
     * @return user
     */
    User findByUsername(String username);
}
