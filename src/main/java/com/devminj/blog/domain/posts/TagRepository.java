package com.devminj.blog.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("Select distinct t.name From Tag t")
    List<String> findAllDistinctName();

    @Query("Select count(*) from Tag t where t.name= :name and post_id is not null")
    Long NumberOfName(@Param("name") String name);
}
