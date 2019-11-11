package com.clone.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.reddit.models.Post;
import com.clone.reddit.models.User;
import com.clone.reddit.models.Vote;
 
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}