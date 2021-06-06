package com.devminj.blog.config.auth.dto;

import com.devminj.blog.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String siteId;
    private String picture;
    private String platform;
    private String role;

    public SessionUser(User user) {
        this.siteId = user.getSiteId();
        this.picture = user.getPicture();
        this.platform = user.getPlatform();
        this.role = user.getRoleKey();
    }
}
