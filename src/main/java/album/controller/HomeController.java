package album.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import album.model.Category;
import album.model.Photo;
import album.repository.CategoryRepository;
import album.repository.PhotoRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private @Autowired PhotoRepository photoRepository;
	private @Autowired CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(Model model) {
	    List<Category> categoryList = categoryRepository.findAll();
	    Map<String, Photo> categoryPhotoMap = new HashMap<>();

	    for (Category category : categoryList) {
	        String categoryName = category.getName(); 
	        if (!categoryPhotoMap.containsKey(categoryName)) {
	            List<Photo> categoryPhotos = photoRepository.findByCategories(category);
	            if (!categoryPhotos.isEmpty()) {
	            	for(Photo photo : categoryPhotos) {
	            		if (!categoryPhotoMap.containsValue(photo)) 
	            			 categoryPhotoMap.put(categoryName, photo);
	            		else
	            			 categoryPhotoMap.put(categoryName, categoryPhotos.get(0));
	            	}
	               
	            }
	        }
	    }

	    model.addAttribute("categoryPhotoMap", categoryPhotoMap);
	    return "home/home";
	}

	


}
