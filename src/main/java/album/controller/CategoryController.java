package album.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import album.model.Category;
import album.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private @Autowired CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		return "categories/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		Optional<Category> category = categoryRepository.findById(id);
		model.addAttribute("category", category.get());
		return "categories/show";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("category", new Category());
		return "categories/create";
	}

	@PostMapping("/store")
	public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "photos/create";
		}
		categoryRepository.save(formCategory);
		return "redirect:/categories/" + formCategory.getId();
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
//		AddDefaultCharsetFilter referer
		HttpSession session = request.getSession();
		String referer = request.getHeader("referer");
		if (referer != null)
			session.setAttribute("referer", referer);
		else
			session.setAttribute("referer", "/categories/" + id);
//		AddDefaultCharsetFilter categories
		Category category = categoryRepository.getReferenceById(id);
		model.addAttribute("category", category);
		return "categories/edit";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute Category formCategory, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "categories/edit";
		}
		categoryRepository.save(formCategory);
		String referer = (String) session.getAttribute("referer");
		return "redirect:" + referer;
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, HttpServletRequest request) {
		categoryRepository.deleteById(id);
		String referer = request.getHeader(HttpHeaders.REFERER);
		return "redirect:" + referer;
	}

}
