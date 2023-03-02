package album.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
