package com.devminj.blog.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    @Query("select p from Post p where p.title LIKE %:keyword% order by p.id DESC")
    List<Post> findByKeyWordDesc(@Param("keyword") String keyword);
}
