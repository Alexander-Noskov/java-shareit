package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;

@Component
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getUser().getName())
                .created(comment.getCreated().toLocalDateTime())
                .build();
    }

    public Comment toComment(CreateCommentDto createCommentDto) {
        if (createCommentDto == null) {
            return null;
        }
        return Comment.builder()
                .text(createCommentDto.getText())
                .build();
    }
}
