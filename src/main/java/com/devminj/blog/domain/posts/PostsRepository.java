package com.devminj.blog.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();
}
