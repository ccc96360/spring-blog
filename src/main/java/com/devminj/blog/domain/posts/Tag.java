package com.devminj.blog.domain.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="post_id")
    private Long postId;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
