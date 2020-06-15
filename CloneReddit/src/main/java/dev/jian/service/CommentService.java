package dev.jian.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.jian.dto.CommentsDto;
import dev.jian.exceptions.PostNotFoundException;
import dev.jian.mapper.CommentMapper;
import dev.jian.model.Comment;
import dev.jian.model.NotificationEmail;
import dev.jian.model.Post;
import dev.jian.model.User;
import dev.jian.repository.CommentRepository;
import dev.jian.repository.PostRepository;
import dev.jian.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;	// Get the current login user
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	
	public void save(CommentsDto commentsDto) {
		
		Post post = postRepository.findById(commentsDto.getPostId())
			.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		System.out.println("*****Post: " + post);
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		System.out.println("*****CommentsDto: " + commentsDto);
		System.out.println("*****CurrentUser: " + authService.getCurrentUser());
		System.out.println("!!!!!!!!!Comment: " + comment);
		commentRepository.save(comment);
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
		System.out.println("Getting email message: " + message);
		sendCommentNotification(message, post.getUser());
		System.out.println("After sending email");
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
