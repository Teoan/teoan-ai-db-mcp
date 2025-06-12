package com.teoan.teoanaimcpdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student", schema = "tclass", catalog = "tclass")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "name", nullable = false, length = 10)
    private String name;


    @Size(max = 10)
    @NotNull
    @Column(name = "role_id", nullable = false, length = 10)
    private Integer roleId;


    @Size(max = 10)
    @NotNull
    @Column(name = "nation_id", nullable = false, length = 10)
    private Integer nationId;


    @Size(max = 10)
    @NotNull
    @Column(name = "politic_id", nullable = false, length = 10)
    private Integer politicId;


    @Size(max = 10)
    @NotNull
    @Column(name = "pos_id", nullable = false, length = 10)
    private Integer posId;

    @Size(max = 64)
    @NotNull
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Size(max = 4)
    @NotNull
    @Column(name = "gender", nullable = false, length = 4)
    private String gender;

    @Size(max = 20)
    @NotNull
    @Column(name = "native_place", nullable = false, length = 20)
    private String nativePlace;

    @Size(max = 20)
    @Column(name = "email", length = 20)
    private String email;

    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Size(max = 255)
    @NotNull
    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @ColumnDefault("0")
    @Column(name = "deleted")
    private Boolean deleted;

}