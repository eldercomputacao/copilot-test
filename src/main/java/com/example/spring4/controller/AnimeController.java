package com.example.spring4.controller;

import com.example.spring4.model.Anime;
import com.example.spring4.dto.CreateAnimeDTO;
import com.example.spring4.service.AnimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/animes")
public class AnimeController {
	private final AnimeService animeService;

	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@GetMapping
	public List<Anime> getAllAnimes() {
		return animeService.findAll();
	}

	@GetMapping("/{id}")
	public Anime getAnimeById(@PathVariable Long id) {
		return animeService.findById(id);
	}

	@PostMapping
	public Anime saveAnime(@RequestBody CreateAnimeDTO createAnimeDTO) {
		Anime anime = createAnimeDTO.toAnime();
		return animeService.save(anime);
	}

	@PutMapping("/{id}/episodes")
	public Anime updateAnimeEpisodes(@PathVariable Long id, @RequestBody Integer episodes) {
		return animeService.updateEpisodes(id, episodes);
	}
}