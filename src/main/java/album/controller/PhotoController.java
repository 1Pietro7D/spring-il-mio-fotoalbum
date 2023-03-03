package album.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import album.model.Category;
import album.model.Photo;
import album.repository.CategoryRepository;
import album.repository.PhotoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/photos")
public class PhotoController {
	// iniettiamo automaticamente 
	
		private @Autowired PhotoRepository photoRepository;
		private @Autowired CategoryRepository categoryRepository;
		
		@GetMapping
		public String index(Model modIndex) {
			List<Photo> photoList = photoRepository.findAll(); 
			modIndex.addAttribute("photoList", photoList);
			return "photos/index";		
		}
		
		@GetMapping("/{id}")
		public String show(@PathVariable("id") Long id, Model model) {
			Optional<Photo> photo = photoRepository.findById(id); 
			model.addAttribute("photo", photo.get());
			return "photos/show";
		}


}
