package br.wbm.testesestudo.domain;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet createPlanet(Planet planet){

        return planetRepository.save(planet);
    }

    public Optional<Planet> getPlanetById(long id){
        return planetRepository.findById(id);
    }

    public Optional<Planet> getPlanetByName(String nome) {
        return planetRepository.findByName(nome);
    }

    public List<Planet> listPlanets(String terrain, String climate) {
        //artigo sobre essa Example: https://www.baeldung.com/spring-data-query-by-example
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));
        return planetRepository.findAll(query);
    }

    public void remove(long id) {
        planetRepository.deleteById(id);
    }
}
