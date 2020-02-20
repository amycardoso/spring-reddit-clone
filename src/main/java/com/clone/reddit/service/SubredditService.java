package com.clone.reddit.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.reddit.dto.SubredditDto;
import com.clone.reddit.exception.SpringRedditException;
import com.clone.reddit.exception.SubredditNotFoundException;
import com.clone.reddit.mapper.SubredditMapper;
import com.clone.reddit.models.Subreddit;
import com.clone.reddit.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
 
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
 
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }
 
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }
 
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}