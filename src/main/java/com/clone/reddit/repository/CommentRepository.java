package com.clone.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.reddit.models.Comment;
import com.clone.reddit.models.Post;
import com.clone.reddit.models.User;
 
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
 
    List<Comment> findAllByUser(User user);
}