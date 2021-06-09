package com.devminj.blog.service.tag;

import com.devminj.blog.domain.posts.TagRepository;
import com.devminj.blog.service.tag.dto.TagCountByNameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public List<TagCountByNameResponseDto> findNameAndCount(){
        return tagRepository.countGroupByName();
    }

}
