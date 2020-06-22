package dev.jian.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jian.service.AuthService;

import dev.jian.dto.SubredditDto;
import dev.jian.exceptions.SpringRedditException;
import dev.jian.mapper.SubredditMapper;
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
	private final SubredditMapper subredditMapper;
	private final AuthService authService;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto, authService.getCurrentUser()));
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
				.orElseThrow(() -> new SpringRedditException("No subreddit found with id - " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
