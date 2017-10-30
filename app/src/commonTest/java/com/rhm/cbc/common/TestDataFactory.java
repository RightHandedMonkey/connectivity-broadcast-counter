package com.rhm.cbc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.rhm.cbc.data.model.response.NamedResource;
import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.response.Sprites;
import com.rhm.cbc.data.model.response.Statistic;

/**
 * Factory class that makes instances of data models with random field values. The aim of this class
 * is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final Random random = new Random();

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static ChangeEvent makePokemon(int id) {
        ChangeEvent pokemon = new ChangeEvent();
        pokemon.setId(id);
        pokemon.setSsid(randomUuid() + id);
        return pokemon;
    }

    public static List<Integer> makePokemonNamesList(int count) {
        List<Integer> pokemonList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            pokemonList.add(makePokemon(i).getId());
        }
        return pokemonList;
    }

    public static List<Integer> makePokemonNameList(List<NamedResource> pokemonList) {
        List<Integer> names = new ArrayList<>();
        for (NamedResource pokemon : pokemonList) {
            names.add(pokemon.name);
        }
        return names;
    }

    public static Statistic makeStatistic() {
        Statistic statistic = new Statistic();
        statistic.baseStat = random.nextInt();
        statistic.stat = makeNamedResource(randomUuid());
        return statistic;
    }

    public static List<Statistic> makeStatisticList(int count) {
        List<Statistic> statisticList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            statisticList.add(makeStatistic());
        }
        return statisticList;
    }

    public static Sprites makeSprites() {
        Sprites sprites = new Sprites();
        sprites.frontDefault = randomUuid();
        return sprites;
    }

    public static NamedResource makeNamedResource(String unique) {
        NamedResource namedResource = new NamedResource();
        namedResource.name = (int) Math.random()*2000;
        namedResource.url = randomUuid();
        return namedResource;
    }

    public static List<NamedResource> makeNamedResourceList(int count) {
        List<NamedResource> namedResourceList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            namedResourceList.add(makeNamedResource(String.valueOf(i)));
        }
        return namedResourceList;
    }
}
