package com.rhm.cbc.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.remote.PokemonService;
import io.reactivex.Single;

/**
 * Created by shivam on 29/5/17.
 */
@Singleton
public class DataManager {

    private PokemonService pokemonService;

    @Inject
    public DataManager(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public Single<List<Integer>> getPokemonList(int limit) {
        return pokemonService
                .getPokemonList(limit)
                .toObservable()
                .flatMapIterable(namedResources -> namedResources.results)
                .map(namedResource -> namedResource.name)
                .toList();
    }

    public Single<ChangeEvent> getPokemon(String name) {
        return pokemonService.getPokemon(name);
    }
}
