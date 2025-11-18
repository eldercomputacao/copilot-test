package com.example.spring4.controller;

import com.example.spring4.model.Anime;
import com.example.spring4.service.AnimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(AnimeController.class)
@DisplayName("Testes para AnimeController")
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimeService animeService;

    private List<Anime> animesList;
    private Anime naruto;
    private Anime onePiece;

    @BeforeEach
    void setUp() {
        naruto = new Anime(1L, "Naruto", 220);
        onePiece = new Anime(2L, "One Piece", 1000);
        Anime attackOnTitan = new Anime(3L, "Attack on Titan", 87);
        Anime deathNote = new Anime(4L, "Death Note", 37);
        Anime dragonBallZ = new Anime(5L, "Dragon Ball Z", 291);
        
        animesList = Arrays.asList(naruto, onePiece, attackOnTitan, deathNote, dragonBallZ);
    }

    @Test
    @DisplayName("GET /animes - Deve retornar lista de todos os animes")
    void getAllAnimes_DeveRetornarListaCompleta() throws Exception {
        // Given
        when(animeService.findAll()).thenReturn(animesList);

        // When & Then
        mockMvc.perform(get("/animes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Naruto")))
                .andExpect(jsonPath("$[0].episodes", is(220)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("One Piece")))
                .andExpect(jsonPath("$[1].episodes", is(1000)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Attack on Titan")))
                .andExpect(jsonPath("$[2].episodes", is(87)))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("Death Note")))
                .andExpect(jsonPath("$[3].episodes", is(37)))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].name", is("Dragon Ball Z")))
                .andExpect(jsonPath("$[4].episodes", is(291)));

        verify(animeService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /animes - Deve retornar lista vazia quando não há animes")
    void getAllAnimes_QuandoListaVazia_DeveRetornarArrayVazio() throws Exception {
        // Given
        when(animeService.findAll()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/animes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(animeService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve retornar anime específico quando ID válido")
    void getAnimeById_ComIdValido_DeveRetornarAnime() throws Exception {
        // Given
        Long id = 1L;
        when(animeService.findById(id)).thenReturn(naruto);

        // When & Then
        mockMvc.perform(get("/animes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Naruto")))
                .andExpect(jsonPath("$.episodes", is(220)));

        verify(animeService, times(1)).findById(id);
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve retornar anime One Piece quando ID é 2")
    void getAnimeById_ComId2_DeveRetornarOnePiece() throws Exception {
        // Given
        Long id = 2L;
        when(animeService.findById(id)).thenReturn(onePiece);

        // When & Then
        mockMvc.perform(get("/animes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("One Piece")))
                .andExpect(jsonPath("$.episodes", is(1000)));

        verify(animeService, times(1)).findById(id);
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve retornar resposta vazia quando anime não existe")
    void getAnimeById_ComIdInexistente_DeveRetornarNull() throws Exception {
        // Given
        Long idInexistente = 999L;
        when(animeService.findById(idInexistente)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/animes/{id}", idInexistente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(animeService, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve aceitar ID como string numérica")
    void getAnimeById_ComIdComoString_DeveConverter() throws Exception {
        // Given
        when(animeService.findById(1L)).thenReturn(naruto);

        // When & Then
        mockMvc.perform(get("/animes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Naruto")));

        verify(animeService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve retornar erro 400 para ID inválido")
    void getAnimeById_ComIdInvalido_DeveRetornarBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/animes/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(animeService, never()).findById(any());
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve funcionar com IDs grandes")
    void getAnimeById_ComIdGrande_DeveFuncionar() throws Exception {
        // Given
        Long idGrande = 999999999L;
        when(animeService.findById(idGrande)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/animes/{id}", idGrande)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(animeService, times(1)).findById(idGrande);
    }

    @Test
    @DisplayName("GET /animes - Deve verificar headers de resposta")
    void getAllAnimes_DeveVerificarHeaders() throws Exception {
        // Given
        when(animeService.findAll()).thenReturn(animesList);

        // When & Then
        mockMvc.perform(get("/animes"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"));

        verify(animeService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve verificar headers de resposta")
    void getAnimeById_DeveVerificarHeaders() throws Exception {
        // Given
        when(animeService.findById(1L)).thenReturn(naruto);

        // When & Then
        mockMvc.perform(get("/animes/1"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"));

        verify(animeService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve verificar que o serviço é chamado corretamente em múltiplas requisições")
    void multiplasRequisicoes_DevemChamarServicoCorretamente() throws Exception {
        // Given
        when(animeService.findAll()).thenReturn(animesList);
        when(animeService.findById(1L)).thenReturn(naruto);
        when(animeService.findById(2L)).thenReturn(onePiece);

        // When & Then
        mockMvc.perform(get("/animes")).andExpect(status().isOk());
        mockMvc.perform(get("/animes/1")).andExpect(status().isOk());
        mockMvc.perform(get("/animes/2")).andExpect(status().isOk());

        verify(animeService, times(1)).findAll();
        verify(animeService, times(1)).findById(1L);
        verify(animeService, times(1)).findById(2L);
    }

    @Test
    @DisplayName("GET /animes - Deve verificar estrutura JSON da resposta")
    void getAllAnimes_DeveVerificarEstruturaJSON() throws Exception {
        // Given
        when(animeService.findAll()).thenReturn(animesList);

        // When & Then
        mockMvc.perform(get("/animes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].name").exists())
                .andExpect(jsonPath("$[*].episodes").exists());

        verify(animeService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /animes/{id} - Deve verificar estrutura JSON da resposta para anime individual")
    void getAnimeById_DeveVerificarEstruturaJSON() throws Exception {
        // Given
        when(animeService.findById(1L)).thenReturn(naruto);

        // When & Then
        mockMvc.perform(get("/animes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.episodes").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.episodes").isNumber());

        verify(animeService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("PUT /animes/{id}/episodes - Deve atualizar episódios quando ID válido")
    void updateAnimeEpisodes_ComIdValido_DeveAtualizarEpisodios() throws Exception {
        // Given
        Long animeId = 1L;
        Integer novosEpisodios = 300;
        Anime animeAtualizado = new Anime(1L, "Naruto", 300);
        
        when(animeService.updateEpisodes(animeId, novosEpisodios)).thenReturn(animeAtualizado);

        // When & Then
        mockMvc.perform(put("/animes/{id}/episodes", animeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("300"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Naruto")))
                .andExpect(jsonPath("$.episodes", is(300)));

        verify(animeService, times(1)).updateEpisodes(animeId, novosEpisodios);
    }
    
    @Test
    @DisplayName("PUT /animes/{id}/episodes - Deve retornar resposta vazia quando anime não existe")
    void updateAnimeEpisodes_ComIdInexistente_DeveRetornarNull() throws Exception {
        // Given
        Long animeIdInexistente = 999L;
        Integer novosEpisodios = 100;
        
        when(animeService.updateEpisodes(animeIdInexistente, novosEpisodios)).thenReturn(null);

        // When & Then
        mockMvc.perform(put("/animes/{id}/episodes", animeIdInexistente)
                .contentType(MediaType.APPLICATION_JSON)
                .content("100"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(animeService, times(1)).updateEpisodes(animeIdInexistente, novosEpisodios);
    }
    
    @Test
    @DisplayName("PUT /animes/{id}/episodes - Deve funcionar com zero episódios")
    void updateAnimeEpisodes_ComZeroEpisodios_DeveFuncionar() throws Exception {
        // Given
        Long animeId = 1L;
        Integer zeroEpisodios = 0;
        Anime animeAtualizado = new Anime(1L, "Naruto", 0);
        
        when(animeService.updateEpisodes(animeId, zeroEpisodios)).thenReturn(animeAtualizado);

        // When & Then
        mockMvc.perform(put("/animes/{id}/episodes", animeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.episodes", is(0)));

        verify(animeService, times(1)).updateEpisodes(animeId, zeroEpisodios);
    }
    
    @Test
    @DisplayName("PUT /animes/{id}/episodes - Deve retornar erro 400 para ID inválido")
    void updateAnimeEpisodes_ComIdInvalido_DeveRetornarBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(put("/animes/abc/episodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("100"))
                .andExpect(status().isBadRequest());

        verify(animeService, never()).updateEpisodes(any(), any());
    }
}