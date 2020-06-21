package com.reddit.service;

import com.reddit.dto.SubredditDto;
import com.reddit.exceptions.SubredditNotFoundException;
import com.reddit.mappers.SubredditMapper;
import com.reddit.model.Subreddit;
import com.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private SubredditMapper subredditMapper;


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

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id " + id));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
