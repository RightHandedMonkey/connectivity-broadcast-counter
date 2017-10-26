package com.rhm.cbc.features.main;

import java.util.List;

import com.rhm.cbc.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);
}
