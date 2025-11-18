package com.example.spring4.service;

import com.example.spring4.model.Anime;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnimeService {
	private List<Anime> animes = new ArrayList<>(Arrays.asList(
		new Anime(1L, "Naruto", 220),
		new Anime(2L, "One Piece", 1000),
		new Anime(3L, "Attack on Titan", 87),
		new Anime(4L, "Death Note", 37),
		new Anime(5L, "Dragon Ball Z", 291)
	));

	public List<Anime> findAll() {
		return animes;
	}

	public Anime findById(Long id) {
		return animes.stream()
			.filter(anime -> anime.getId().equals(id))
			.findFirst()
			.orElse(null);
	}

	public Anime save(Anime anime) {
		if (anime.getId() == null) {
			// Gerar novo ID baseado no maior ID existente
			Long maxId = animes.stream()
				.mapToLong(Anime::getId)
				.max()
				.orElse(0L);
			anime.setId(maxId + 1);
		}
		
		// Verificar se já existe um anime com o mesmo ID
		boolean exists = animes.stream()
			.anyMatch(existingAnime -> existingAnime.getId().equals(anime.getId()));
		
		if (exists) {
			// Atualizar anime existente
			for (int i = 0; i < animes.size(); i++) {
				if (animes.get(i).getId().equals(anime.getId())) {
					animes.set(i, anime);
					break;
				}
			}
		} else {
			// Adicionar novo anime
			animes.add(anime);
		}
		
		return anime;
	}

	public Anime updateEpisodes(Long id, Integer episodes) {
		Anime anime = findById(id);
		if (anime == null) {
			return null;
		}
		
		anime.setEpisodes(episodes);
		return anime;
	}
}