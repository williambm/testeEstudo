package br.wbm.testesestudo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.wbm.testesestudo.utils.PlanetsConstants.INVALID_PLANET;
import static br.wbm.testesestudo.utils.PlanetsConstants.PLANET;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = {PlanetService.class, PlanetRepository.class})
@ExtendWith(SpringExtension.class)
class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_QuandoDTOValido_DeveRetornarPlaneta() {

        //Arranjo
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        //Ação
        Planet planet = planetService.createPlanet(PLANET);

        //Asserção
        Assertions.assertEquals(PLANET, planet);
    }

    @Test
    void createPlanet_QuandoDTOInvalido_DeveLancarException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> planetService.createPlanet(INVALID_PLANET));

        //TODO: EU AINDA PODERIA VERIFICAR A MENSAGEM DE ERRO A SER LANÇADA.
//        Assertions.assertEquals("mensagem específica de exceção",exception.getMessage());
    }


    @Test
    void getPlanetById_comIdExistente_deveRetornarPlaneta() {
        //Arranjo
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));

        Optional<Planet> retornoDaBusca = planetService.getPlanetById(1L);

        Assertions.assertNotEquals(Optional.empty(), retornoDaBusca);
        Assertions.assertEquals(PLANET, retornoDaBusca.get());

    }

    @Test
    void getPlanetById_comIdInexistente_deveRetornarOptionalVazio() {
        when(planetRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Planet> retornoDaBusca = planetService.getPlanetById(2L);

        Assertions.assertEquals(Optional.empty(), retornoDaBusca);
    }


    @Test
    void getPlanetById_comNomeExistente_deveRetornarPlaneta() {
        when(planetRepository.findByName("existente")).thenReturn(Optional.of(PLANET));

        Optional<Planet> retornoDaBusca = planetService.getPlanetByName("existente");

        Assertions.assertNotEquals(Optional.empty(), retornoDaBusca);
        Assertions.assertEquals(PLANET, retornoDaBusca.get());
    }

    @Test
    void getPlanetById_comNomeInexistente_deveRetornarOptionalVazio() {
        when(planetRepository.findByName("inexistente")).thenReturn(Optional.empty());

        Optional<Planet> retornoDaBusca = planetService.getPlanetByName("inexistente");

        Assertions.assertEquals(Optional.empty(), retornoDaBusca);
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> planetas = new ArrayList<>();
        planetas.add(PLANET);
        Example<Planet> queryFake = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRepository.findAll(queryFake)).thenReturn(planetas);

        List<Planet> retorno = planetService.listPlanets(PLANET.getTerrain(), PLANET.getClimate());

        Assertions.assertEquals(planetas, retorno);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        //Arranjo
        Example<Planet> queryFake = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRepository.findAll(queryFake)).thenReturn(Collections.emptyList());

        //Ação
        List<Planet> retorno = planetService.listPlanets(PLANET.getTerrain(), PLANET.getClimate());

        //Asserções
        Assertions.assertEquals(0, retorno.size());
        Mockito.verify(planetRepository, times(1)).findAll(queryFake);
        Mockito.verifyNoMoreInteractions(planetRepository);
    }

    @Test
    public void remove_ComIdExistente_NaoDeveLancarException() {
        doNothing().when(planetRepository).deleteById(anyLong());

        Assertions.assertDoesNotThrow(() -> planetService.remove(1L));
    }

    @Test
    public void remove_ComIdInexistente_DeveLancarException() {
        doThrow(RuntimeException.class).when(planetRepository).deleteById(anyLong());

        Assertions.assertThrows(RuntimeException.class, () -> planetService.remove(1L));
    }
}