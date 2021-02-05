package com.example.spring.boot.security.jwt.demo.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Permossion:
 * 权限表
 * @author jzheng
 * @since 2021/1/1 1:53 上午
 */
@Entity
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    private String permissionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
