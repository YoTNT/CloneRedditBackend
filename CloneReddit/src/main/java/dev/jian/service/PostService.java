package dev.jian.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jian.dto.PostResponse;
import dev.jian.exceptions.PostNotFoundException;

import dev.jian.dto.PostRequest;
import dev.jian.exceptions.SubredditNotFoundException;
import dev.jian.mapper.PostMapper;
import dev.jian.model.Post;
import dev.jian.model.Subreddit;
import dev.jian.model.User;
import dev.jian.repository.PostRepository;
import dev.jian.repository.SubredditRepository;
import dev.jian.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public void save(PostRequest postRequest) {
		
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
			.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
	
		postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
	}

	@Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
    
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)	// List<Post>
                .stream()						// Stream<Post>
                .map(postMapper::mapToDto)		// Stream<PostResponse>
                .collect(toList());
    }
}
