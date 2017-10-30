package com.rhm.cbc.data.remote;

import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.response.PokemonListResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("pokemon")
    Single<PokemonListResponse> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Single<ChangeEvent> getPokemon(@Path("name") String name);
}
