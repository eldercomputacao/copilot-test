package com.example.spring4.model;

public class Anime {
	private Long id;
	private String name;
	private Integer episodes;

	public Anime() {
	}

	public Anime(Long id, String name, Integer episodes) {
		this.id = id;
		this.name = name;
		this.episodes = episodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}