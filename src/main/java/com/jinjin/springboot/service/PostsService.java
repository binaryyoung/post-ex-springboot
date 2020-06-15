package com.jinjin.springboot.service;

import com.jinjin.springboot.domain.Posts.Posts;
import com.jinjin.springboot.domain.Posts.PostsRepository;
import com.jinjin.springboot.web.dto.PostsSaveRequestDto;
import com.jinjin.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "not exist post. id : "+id
                ));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
}
