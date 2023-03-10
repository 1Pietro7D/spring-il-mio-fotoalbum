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
import album.repository.CategoryRepository;
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
		return "redirect:/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Category category = categoryRepository.getReferenceById(id);
		model.addAttribute("category", category);
		return "categories/edit";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute Category formCategory, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "categories/edit";
		}
		categoryRepository.save(formCategory);
		return "redirect:/categories/" + formCategory.getId();
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}

}
