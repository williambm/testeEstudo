package br.wbm.testesestudo.web;

import br.wbm.testesestudo.domain.Planet;
import br.wbm.testesestudo.domain.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        Planet planetCreated = planetService.createPlanet(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable int id) {

        return planetService.getPlanetById(id)
                .map(
                        planet -> ResponseEntity.ok(planet)
                )
                .orElse(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                );
    }

    @GetMapping("/name/{nome}")
    public ResponseEntity<Planet> get(@PathVariable String nome) {

        return planetService.getPlanetByName(nome)
                .map(
                        planet -> ResponseEntity.ok(planet)
                )
                .orElse(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                );
    }

    @GetMapping
    public ResponseEntity<List<Planet>> listarPlanetas(
            @RequestParam(required = false) String terrain,
            @RequestParam(required = false) String climate
    ){
        List<Planet>planetasRetornadosDaBusca = planetService.listPlanets(terrain,climate);

        return ResponseEntity.ok(planetasRetornadosDaBusca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable long id){
        planetService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
