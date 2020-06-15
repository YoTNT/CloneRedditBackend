package dev.jian.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.jian.dto.CommentsDto;
import dev.jian.model.Comment;
import dev.jian.model.Post;
import dev.jian.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
	
}
