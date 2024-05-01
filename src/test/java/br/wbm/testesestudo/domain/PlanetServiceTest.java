package br.wbm.testesestudo.domain;

import br.wbm.testesestudo.utils.PlanetsConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.wbm.testesestudo.utils.PlanetsConstants.*;

@SpringBootTest(classes = {PlanetService.class, PlanetRepository.class})
//@ExtendWith(SpringExtension.class)
class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_QuandoDTOValido_DeveRetornarPlaneta() {

        //Arranjo

        //Ação
        Planet planet = planetService.createPlanet(PLANET);

        //Asserção

        Assertions.assertEquals(PLANET,planet);
    }
}