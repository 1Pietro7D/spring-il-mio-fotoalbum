package album.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import album.model.Photo;
import album.repository.PhotoRepository;

@Controller
@RequestMapping("/photos")
public class PhotoController {
	// iniettiamo automaticamente 
		private @Autowired PhotoRepository photoRepository;

		@GetMapping
		public String index(Model modIndex) {
			List<Photo> photoList = photoRepository.findAll(); // restituisce un elenco di istanze libro
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
