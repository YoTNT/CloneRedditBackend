package dev.jian.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.jian.dto.SubredditDto;
import dev.jian.model.Post;
import dev.jian.model.Subreddit;
import dev.jian.model.User;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size();
	}

	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user);
}
