package com.rhm.cbc.features.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhm.cbc.R;
import com.rhm.cbc.data.model.ChangeEvent;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<ChangeEvent> pokemonList;

    @Inject
    PokemonAdapter() {
        pokemonList = Collections.emptyList();
    }

    public void setPokemon(List<ChangeEvent> pokemon) {
        this.pokemonList = pokemon;
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        ChangeEvent pokemon = this.pokemonList.get(position);
        holder.onBind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView nameText;
        @BindView(R.id.text_ssid)
        TextView ssidText;
        @BindView(R.id.text_detailed_state)
        TextView detailedStateText;
        @BindView(R.id.text_event_time)
        TextView eventTimeText;
        @BindView(R.id.text_complete_msg)
        TextView completeMsgText;

        private ChangeEvent pokemon;

        PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBind(ChangeEvent pokemon) {
            this.pokemon = pokemon;
            nameText.setText("TYPE: " + pokemon.getTypeName());
            ssidText.setText("SSID: " + pokemon.getSsid());
            detailedStateText.setText("STATE: " + pokemon.getDetailedState());
            eventTimeText.setText("TIME: " + pokemon.getEventTime());
            completeMsgText.setText(pokemon.getCompleteMsg());
        }
    }
}
