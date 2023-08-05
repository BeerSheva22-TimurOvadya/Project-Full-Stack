package telran.spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import telran.spring.model.Advert;
import telran.spring.service.AdvertService;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/adverts")
@Slf4j
@Validated
public class AdvertController {
	private final AdvertService advertService;

	public AdvertController(AdvertService advertService) {
		this.advertService = advertService;
	}

	@PostMapping
	public Advert addAdvert(@RequestBody @Valid Advert advert) {
		return advertService.addAdvert(advert);
	}

	@GetMapping
	public List<Advert> getAllAdverts() {
		log.info("All adverts have been retrieved");
		return advertService.getAllAdverts();
	}

	@GetMapping("/category/{category}")
	public List<Advert> getAdvertsByCategory(@PathVariable String category) {
		log.info("Adverts have been retrieved by category: " + category);
		return advertService.getAdvertsByCategory(category);
	}

	@GetMapping("/price")
	public List<Advert> getAdvertsByPrice(@RequestParam @Min(0) double maxPrice) {
		log.info("Adverts have been retrieved by max price: " + maxPrice);
		return advertService.getAdvertsByPrice(maxPrice);
	}

	@PutMapping("/{id}")
	public Advert editAdvert(@PathVariable @Min(100000) @Max(999999) int id, @RequestBody @Valid Advert advert) {
		log.info("Edited advert: " + advert);
		return advertService.editAdvert(id, advert);
	}

	@DeleteMapping("/{id}")
	public void deleteAdvert(@PathVariable @Min(100000) @Max(999999) int id) {
		log.info("Deleted advert by ID: " + id);
		advertService.deleteAdvert(id);
	}

	@PostMapping("/batch")
	public List<Advert> addAdverts(@RequestBody @Valid List<Advert> adverts) {
		return advertService.addAdverts(adverts);
	}

	@PreDestroy
	void shutdown() {
		log.info("The server has been stopped");
	}
}
