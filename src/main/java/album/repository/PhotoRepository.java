package album.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import album.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	public List<Photo> findByTitleContainingIgnoreCase(String title);
	public List<Photo> findByTagContainingIgnoreCase(String tag);
}
