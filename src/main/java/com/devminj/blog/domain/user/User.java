package com.devminj.blog.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String siteId;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String picture;

    @Column
    private String platform;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String siteId, String email, String picture, String platform, Role role) {
        this.name = name;
        this.siteId = siteId;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.platform = platform;
    }

    public User update(String picture){
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
