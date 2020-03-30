package com.clone.reddit.service;

import java.util.Optional;

import com.clone.reddit.dto.VoteDto;
import com.clone.reddit.exception.PostNotFoundException;
import com.clone.reddit.exception.SpringRedditException;
import com.clone.reddit.models.Post;
import com.clone.reddit.models.Vote;
import com.clone.reddit.repository.PostRepository;
import com.clone.reddit.repository.VoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static com.clone.reddit.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
 
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
 
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }
 
    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}