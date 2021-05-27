package com.devminj.blog.config.auth.dto;

import com.devminj.blog.domain.user.Role;
import com.devminj.blog.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String siteId;
    private String platform;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String siteId, String platform) {
        this.attributes = attributes;
        this.siteId = siteId;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.platform = platform;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofGithub(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes){
        for(String s : attributes.keySet()){
            System.out.println(s + attributes.get(s));
        }
        System.out.println("name:" + (String) attributes.get("name"));
        System.out.println("email:" + (String) attributes.get("email"));
        System.out.println("avatar_url:" + (String) attributes.get("avatar_url"));
        return OAuthAttributes.builder()
                .siteId((String) attributes.get("login"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .platform("github")
                .build();
    }

    public User toEntity(){
        System.out.println("====toEntity====");
        System.out.println(name);
        System.out.println(email);
        System.out.println(picture);
        return User.builder()
                .siteId(this.siteId)
                .name(this.name)
                .email(this.email)
                .picture(this.picture)
                .role(Role.GUEST)
                .platform(this.platform)
                .build();

    }
}
