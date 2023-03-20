package album.apiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import album.model.Category;
import album.model.Comment;
import album.model.Photo;
import album.repository.CategoryRepository;
import album.repository.CommentRepository;
import album.repository.PhotoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/photos")
public class PhotoApiController {

	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CommentRepository commentRepository;
	

	@GetMapping
	public ResponseEntity<Map<String, List<?>>> index(@RequestParam(required = false) String title, @RequestParam(required = false) Long categoryId) {
	    List<Photo> photos;
	    if (title != null && !title.isBlank()) {
	        photos = photoRepository.findByTitleContainingIgnoreCase(title);
	    } else {
	        photos = photoRepository.findAll();
	    }
	    List<Category> categories = categoryRepository.findAll();

	    // filtra le foto per isVisible = true e per categoria (se specificata)
	    List<Photo> visiblePhotos;
	    if (categoryId != null && categoryId > 0) {
	        visiblePhotos = photos.stream()
	                .filter(p -> p.getVisible() && ((Category) p.getCategories()).getId() == categoryId)
	                .collect(Collectors.toList());
	    } else {
	        visiblePhotos = photos.stream()
	                .filter(Photo::getVisible)
	                .collect(Collectors.toList());
	    }

	    Map<String, List<?>> result = new HashMap<>();
	    result.put("photos", visiblePhotos);
	    result.put("categories", categories);

	    if (result.size() <= 0) {
	        return new ResponseEntity<Map<String, List<?>>>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<Map<String, List<?>>>(result, HttpStatus.OK);
	    }
	}

	@GetMapping("/{id}")
	public ResponseEntity<Photo> show(@PathVariable("id") Long id) {
		Optional<Photo> result = photoRepository.findById(id);
		if (result.isPresent())
			return new ResponseEntity<Photo>(result.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create")
	public ResponseEntity<Photo> create(@RequestBody Photo formPhoto) {
		return new ResponseEntity<Photo>(photoRepository.save(formPhoto), HttpStatus.OK);
	}

	@PutMapping("edit/{id}")
	public ResponseEntity<Photo> update(@RequestBody Photo formPhoto, @PathVariable("id") Long id) {
		Photo photo = photoRepository.getReferenceById(id);
		photo.setTitle(photo.getTitle());
		return new ResponseEntity<Photo>(photoRepository.save(photo), HttpStatus.OK);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Photo> delete(@PathVariable("id") Long id) {
		photoRepository.deleteById(id);
		return new ResponseEntity<Photo>(HttpStatus.OK);
	}

	// Crea Commento
	@PostMapping("/{id}/comments")
	public ResponseEntity<String> addComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			comment.setPhoto(photo);
			commentRepository.save(comment);
			return ResponseEntity.ok("Comment create successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/comments")
	public ResponseEntity<List<Comment>> index(@PathVariable("id") Long id) {
		Optional<Photo> optionalPhoto = photoRepository.findById(id);
		if (optionalPhoto.isPresent()) {
			Photo photo = optionalPhoto.get();
			if (photo.getComments().size() > 0)
				return new ResponseEntity<List<Comment>>(photo.getComments(), HttpStatus.OK);
			else
				return new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
		}
	}
}