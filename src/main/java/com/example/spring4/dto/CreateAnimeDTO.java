package com.example.spring4.dto;

import com.example.spring4.model.Anime;

public class CreateAnimeDTO {
	private String name;
	private Integer episodes;

	public CreateAnimeDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Integer episodes) {
		this.episodes = episodes;
	}

	public Anime toAnime() {
		return new Anime(null, this.name, this.episodes);
	}
}