package com.jinjin.springboot.service;

import com.jinjin.springboot.domain.posts.Posts;
import com.jinjin.springboot.domain.posts.PostsRepository;
import com.jinjin.springboot.web.dto.PostsListResponseDto;
import com.jinjin.springboot.web.dto.PostsResponseDto;
import com.jinjin.springboot.web.dto.PostsSaveRequestDto;
import com.jinjin.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "no exist post. id : " + id
                ));

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되 조회기능만 남겨두어 속도개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if(!postsRepository.existsById(id)) {
            throw new IllegalArgumentException("no exist posts id : " + id);
        }
        postsRepository.deleteById(id);
    }
}
