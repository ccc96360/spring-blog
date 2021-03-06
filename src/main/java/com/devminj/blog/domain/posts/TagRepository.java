package com.devminj.blog.domain.posts;

import com.devminj.blog.service.tag.dto.TagCountByNameResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("Select distinct t.name From Tag t")
    List<String> findAllDistinctName();

    @Query("Select count(*) from Tag t where t.name = :name and post_id is not null")
    Long NumberOfName(@Param("name") String name);

    @Query("Select" +
                " new com.devminj.blog.service.tag.dto.TagCountByNameResponseDto(t.name, count(t))" +
            " from" +
                " Tag t" +
            " group by t.name")
    List<TagCountByNameResponseDto> countGroupByName();

    @Modifying
    @Transactional
    @Query("Delete From Tag where post_id = :id")
    void deleteByPostId(@Param("id") Long id);

    @Query("Select t.post from Tag t where t.name = :name order by t.id desc")
    Page<Post> findAllPostsByTagName(@Param("name") String name, Pageable pageable);

}
