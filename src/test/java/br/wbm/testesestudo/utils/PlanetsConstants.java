package br.wbm.testesestudo.utils;

import br.wbm.testesestudo.domain.Planet;

public class PlanetsConstants {

    //todo: colocar aqui depois esse cara em um factory
    public static final Planet PLANET = new Planet("name","climate","terrain");
    public static final Planet INVALID_PLANET = new Planet("","","");
}
