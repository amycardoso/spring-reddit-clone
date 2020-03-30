package com.clone.reddit.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import com.clone.reddit.dto.CommentsDto;
import com.clone.reddit.exception.PostNotFoundException;
import com.clone.reddit.mapper.CommentMapper;
import com.clone.reddit.models.Comment;
import com.clone.reddit.models.NotificationEmail;
import com.clone.reddit.models.Post;
import com.clone.reddit.models.User;
import com.clone.reddit.repository.CommentRepository;
import com.clone.reddit.repository.PostRepository;
import com.clone.reddit.repository.UserRepository;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    @Autowired PostRepository postRepository;
    @Autowired UserRepository userRepository;
    @Autowired AuthService authService;
    @Autowired CommentMapper commentMapper;
    @Autowired CommentRepository commentRepository;
    @Autowired MailContentBuilder mailContentBuilder;
    @Autowired MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}