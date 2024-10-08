package com.example.gterp.entity.user;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(length = 250)
    private String email;

    @Column(length = 250)
    private String phoneNumber;

    @Column(length = 250)
    private String nickName;

    @Column
    private String permission;

    @Transient
    private boolean _delete;

    // 添加软删除字段
    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted = false;

    // 复制器
    public void copyNonNullPropertiesFrom(User other) {
        if (other == null) {
            return;
        }
        if (this.username == null && other.username != null) {
            this.username = other.username;
        }
        if (this.password == null && other.password != null) {
            this.password = other.password;
        }
        if (this.email == null && other.email != null) {
            this.email = other.email;
        }
        if (this.phoneNumber == null && other.phoneNumber != null) {
            this.phoneNumber = other.phoneNumber;
        }
        if (this.nickName == null && other.nickName != null) {
            this.nickName = other.nickName;
        }
        if (this.permission == null && other.permission != null) {
            this.permission = other.permission;
        }
    }

    // Getter 和 Setter
    public boolean isDelete() {
        return _delete;
    }

    public void setDelete(boolean _delete) {
        this._delete = _delete;
    }

    // 添加 isDeleted 的 Getter 和 Setter
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}



