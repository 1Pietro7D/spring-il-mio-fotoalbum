package album.apiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiPhotos")
public class PhotoLayoutController {
	
	@GetMapping
	public String index(){
		return "api/photos/index";
	}
	
	@GetMapping("/show")
	public String show(){
		return "api/photos/show";
	}
}
