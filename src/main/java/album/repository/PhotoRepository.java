package album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import album.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
