package com.rhm.cbc.features.detail;

import com.rhm.cbc.data.model.response.Pokemon;
import com.rhm.cbc.data.model.response.Statistic;
import com.rhm.cbc.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);
}
