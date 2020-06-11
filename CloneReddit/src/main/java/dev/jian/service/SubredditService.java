package dev.jian.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jian.dto.SubredditDto;
import dev.jian.model.Subreddit;
import dev.jian.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = mapSubredditDto(subredditDto);
		Subreddit save = subredditRepository.save(subreddit);
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
		return Subreddit.builder().name(subredditDto.getName())
			.description(subredditDto.getDescription())
			.build();
		
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		
		return subredditRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(toList());
				
	}
	
	private SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName())
				.id(subreddit.getId())
				.numberOfPosts(subreddit.getPosts().size())
				.build();
	}
}
