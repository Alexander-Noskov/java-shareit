package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.item i join fetch c.user u where i.id = :itemId order by c.created desc")
    List<Comment> findAllByItemId(@Param("itemId") Long itemId);
}
