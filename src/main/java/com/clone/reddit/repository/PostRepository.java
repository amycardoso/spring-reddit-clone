package com.clone.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.reddit.models.Post;
import com.clone.reddit.models.Subreddit;
import com.clone.reddit.models.User;
 
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
 
    List<Post> findByUser(User user);
}