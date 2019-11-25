package com.rest.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
public class Users {

    private Long pid;
    private String name;
    private String email;
    private byte[] password;
    private Date regdate;
    private Date bdate;
    private String gender;
    private byte[] image;
    private String status;
    private String role;
    private String veryfyCode;
    private String imageType;

    public Users() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID",length = 100)
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Column(name = "NAME",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "EMAIL",nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Lob
    @Column(name = "PASSWORD",nullable = false)
    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Column(name = "REGISTATION_DATE",nullable = false)
    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    @Column(name = "BRITH_DATE",nullable = false)
    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    @Column(name = "GENDER",nullable = false)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Lob
    @Column(name = "PROFILE_PICTURE")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Column(name = "STATUS",nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "ROLE",nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "VERYFY_CODE",nullable = false)
    public String getVeryfyCode() {
        return veryfyCode;
    }

    public void setVeryfyCode(String veryfyCode) {
        this.veryfyCode = veryfyCode;
    }

    @Column(name = "PROFILE_IMAGE_FORMAT")
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
