package album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import album.model.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}