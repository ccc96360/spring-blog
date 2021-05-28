package com.devminj.blog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile(){
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "rea1", "real2");
        String defaultsProfile = profiles.isEmpty()? "default" : profiles.get(0);
        for(String s: profiles){
            System.out.println("Active Profile:" + s);
        }
        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultsProfile);
    }

}
