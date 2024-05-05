package br.wbm.testesestudo.domain;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet,Long>, QueryByExampleExecutor<Planet> {
    Optional<Planet> findByName(String nome);

    @Override
    <S extends Planet> List<S> findAll(Example<S> example);
}
