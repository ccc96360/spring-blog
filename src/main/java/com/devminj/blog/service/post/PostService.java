package com.devminj.blog.service.post;

import com.devminj.blog.domain.posts.Post;
import com.devminj.blog.domain.posts.PostsRepository;
import com.devminj.blog.domain.posts.TagRepository;
import com.devminj.blog.service.post.dto.PostListResponseDto;
import com.devminj.blog.service.post.dto.PostResponseDto;
import com.devminj.blog.service.post.dto.PostSaveRequestDto;
import com.devminj.blog.service.post.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository postsRepository;
    private final TagRepository tagRepository;
    @Transactional
    public Long save(PostSaveRequestDto postSaveRequestDto){
        return postsRepository.save(postSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto){
        Post post = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재 하지 않습니다. id = " + id));
        tagRepository.deleteByPostId(post.getId());
        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getTags());
        return post.getId();
    }

    @Transactional
    public void delete(Long id){
        Post post = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("삭제하고자 하는 게시글이 존재 하지 않습니다 ID = " + id));
        postsRepository.delete(post);
    }

    public PostResponseDto findById(Long id){
        Post post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 게시글에 접근 했습니다. id =" + id));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public Page<PostListResponseDto> findByTagName(String name, Pageable pageable){
        return tagRepository.findAllPostsByTagName(name, pageable)
                .map(PostListResponseDto::new);
    }
    @Transactional
    public Page<PostListResponseDto> findByKeyWord(String keyword, Pageable pageable){
        return postsRepository.findByTitleContaining(keyword, pageable)
                .map(PostListResponseDto::new);
    }
    @Transactional
    public Page<PostListResponseDto> findAllWithPage(Pageable pageable){
        return postsRepository.findAll(pageable).map(PostListResponseDto::new);
    }
}
