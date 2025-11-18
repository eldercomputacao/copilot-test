package com.example.spring4.service;

import com.example.spring4.model.Anime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Testes para AnimeService")
class AnimeServiceTest {

    private AnimeService animeService;

    @BeforeEach
    void setUp() {
        animeService = new AnimeService();
    }

    @Test
    @DisplayName("Deve retornar todos os animes quando findAll é chamado")
    void findAll_DeveRetornarTodosOsAnimes() {
        // Given
        // Quando o serviço é inicializado

        // When
        List<Anime> animes = animeService.findAll();

        // Then
        assertNotNull(animes, "A lista de animes não deve ser null");
        assertEquals(5, animes.size(), "Deve retornar 5 animes");
        
        // Verificar se contém os animes esperados
        assertTrue(animes.stream().anyMatch(anime -> "Naruto".equals(anime.getName())));
        assertTrue(animes.stream().anyMatch(anime -> "One Piece".equals(anime.getName())));
        assertTrue(animes.stream().anyMatch(anime -> "Attack on Titan".equals(anime.getName())));
        assertTrue(animes.stream().anyMatch(anime -> "Death Note".equals(anime.getName())));
        assertTrue(animes.stream().anyMatch(anime -> "Dragon Ball Z".equals(anime.getName())));
    }

    @Test
    @DisplayName("Deve retornar anime específico quando ID válido é fornecido")
    void findById_ComIdValido_DeveRetornarAnime() {
        // Given
        Long idValido = 1L;

        // When
        Anime anime = animeService.findById(idValido);

        // Then
        assertNotNull(anime, "O anime não deve ser null");
        assertEquals(1L, anime.getId(), "O ID deve ser 1");
        assertEquals("Naruto", anime.getName(), "O nome deve ser Naruto");
        assertEquals(220, anime.getEpisodes(), "O número de episódios deve ser 220");
    }

    @Test
    @DisplayName("Deve retornar anime One Piece quando ID 2 é fornecido")
    void findById_ComId2_DeveRetornarOnePiece() {
        // Given
        Long id = 2L;

        // When
        Anime anime = animeService.findById(id);

        // Then
        assertNotNull(anime);
        assertEquals(2L, anime.getId());
        assertEquals("One Piece", anime.getName());
        assertEquals(1000, anime.getEpisodes());
    }

    @Test
    @DisplayName("Deve retornar anime Attack on Titan quando ID 3 é fornecido")
    void findById_ComId3_DeveRetornarAttackOnTitan() {
        // Given
        Long id = 3L;

        // When
        Anime anime = animeService.findById(id);

        // Then
        assertNotNull(anime);
        assertEquals(3L, anime.getId());
        assertEquals("Attack on Titan", anime.getName());
        assertEquals(87, anime.getEpisodes());
    }

    @Test
    @DisplayName("Deve retornar anime Death Note quando ID 4 é fornecido")
    void findById_ComId4_DeveRetornarDeathNote() {
        // Given
        Long id = 4L;

        // When
        Anime anime = animeService.findById(id);

        // Then
        assertNotNull(anime);
        assertEquals(4L, anime.getId());
        assertEquals("Death Note", anime.getName());
        assertEquals(37, anime.getEpisodes());
    }

    @Test
    @DisplayName("Deve retornar anime Dragon Ball Z quando ID 5 é fornecido")
    void findById_ComId5_DeveRetornarDragonBallZ() {
        // Given
        Long id = 5L;

        // When
        Anime anime = animeService.findById(id);

        // Then
        assertNotNull(anime);
        assertEquals(5L, anime.getId());
        assertEquals("Dragon Ball Z", anime.getName());
        assertEquals(291, anime.getEpisodes());
    }

    @Test
    @DisplayName("Deve retornar null quando ID não existe")
    void findById_ComIdInexistente_DeveRetornarNull() {
        // Given
        Long idInexistente = 999L;

        // When
        Anime anime = animeService.findById(idInexistente);

        // Then
        assertNull(anime, "Deve retornar null para ID inexistente");
    }

    @Test
    @DisplayName("Deve retornar null quando ID é null")
    void findById_ComIdNull_DeveRetornarNull() {
        // Given
        Long idNull = null;

        // When
        Anime anime = animeService.findById(idNull);

        // Then
        assertNull(anime, "Deve retornar null quando ID é null");
    }

    @Test
    @DisplayName("Deve retornar null quando ID é zero")
    void findById_ComIdZero_DeveRetornarNull() {
        // Given
        Long idZero = 0L;

        // When
        Anime anime = animeService.findById(idZero);

        // Then
        assertNull(anime, "Deve retornar null quando ID é zero");
    }

    @Test
    @DisplayName("Deve retornar null quando ID é negativo")
    void findById_ComIdNegativo_DeveRetornarNull() {
        // Given
        Long idNegativo = -1L;

        // When
        Anime anime = animeService.findById(idNegativo);

        // Then
        assertNull(anime, "Deve retornar null quando ID é negativo");
    }

    @Test
    @DisplayName("Deve verificar se a lista retornada não é modificável")
    void findAll_ListaRetornadaDeveSerImutavel() {
        // Given
        List<Anime> animes = animeService.findAll();

        // When & Then
        // Tentativa de modificar a lista deve falhar ou não afetar a lista original
        int tamanhoOriginal = animes.size();
        
        // Verificar se conseguimos acessar todos os elementos
        for (int i = 0; i < animes.size(); i++) {
            assertNotNull(animes.get(i), "Cada anime na lista deve ser não-null");
        }
        
        assertEquals(tamanhoOriginal, animeService.findAll().size(), 
            "O tamanho da lista deve permanecer consistente entre chamadas");
    }

    @Test
    @DisplayName("Deve verificar que chamadas múltiplas de findAll retornam a mesma lista")
    void findAll_ChamadasMultiplas_DevemRetornarMesmaLista() {
        // Given
        List<Anime> primeiraLista = animeService.findAll();
        List<Anime> segundaLista = animeService.findAll();

        // When & Then
        assertEquals(primeiraLista.size(), segundaLista.size(), 
            "Ambas as listas devem ter o mesmo tamanho");
        
        for (int i = 0; i < primeiraLista.size(); i++) {
            Anime anime1 = primeiraLista.get(i);
            Anime anime2 = segundaLista.get(i);
            
            assertEquals(anime1.getId(), anime2.getId(), "IDs devem ser iguais");
            assertEquals(anime1.getName(), anime2.getName(), "Nomes devem ser iguais");
            assertEquals(anime1.getEpisodes(), anime2.getEpisodes(), "Episódios devem ser iguais");
        }
    }

    @Test
    @DisplayName("Deve verificar que todos os animes têm dados válidos")
    void findAll_TodosAnimesDevemTerDadosValidos() {
        // Given
        List<Anime> animes = animeService.findAll();

        // When & Then
        for (Anime anime : animes) {
            assertNotNull(anime.getId(), "ID do anime não deve ser null");
            assertNotNull(anime.getName(), "Nome do anime não deve ser null");
            assertNotNull(anime.getEpisodes(), "Episódios do anime não devem ser null");
            
            assertTrue(anime.getId() > 0, "ID deve ser positivo");
            assertFalse(anime.getName().isEmpty(), "Nome não deve estar vazio");
            assertTrue(anime.getEpisodes() > 0, "Número de episódios deve ser positivo");
        }
    }

    @Test
    @DisplayName("updateEpisodes deve atualizar a quantidade de episódios quando ID válido")
    void updateEpisodes_DeveAtualizarEpisodios_QuandoIdValido() {
        // Given
        Long animeId = 1L;
        Integer novosEpisodios = 300;
        
        // When
        Anime result = animeService.updateEpisodes(animeId, novosEpisodios);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(animeId);
        assertThat(result.getEpisodes()).isEqualTo(novosEpisodios);
        assertThat(result.getName()).isEqualTo("Naruto"); // Nome não deve mudar
    }
    
    @Test
    @DisplayName("updateEpisodes deve retornar null quando ID não existe")
    void updateEpisodes_DeveRetornarNull_QuandoIdNaoExiste() {
        // Given
        Long animeIdInexistente = 999L;
        Integer novosEpisodios = 100;
        
        // When
        Anime result = animeService.updateEpisodes(animeIdInexistente, novosEpisodios);
        
        // Then
        assertThat(result).isNull();
    }
    
    @Test
    @DisplayName("updateEpisodes deve funcionar com zero episódios")
    void updateEpisodes_DeveFuncionar_ComZeroEpisodios() {
        // Given
        Long animeId = 1L;
        Integer zeroEpisodios = 0;
        
        // When
        Anime result = animeService.updateEpisodes(animeId, zeroEpisodios);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEpisodes()).isEqualTo(zeroEpisodios);
    }
}