package com.rhm.cbc.features.main;

import javax.inject.Inject;

import com.rhm.cbc.data.CBCDatabase;
import com.rhm.cbc.data.DataManager;
import com.rhm.cbc.features.base.BasePresenter;
import com.rhm.cbc.injection.ApplicationContext;
import com.rhm.cbc.injection.ConfigPersistent;
import com.rhm.cbc.injection.component.AppComponent;
import com.rhm.cbc.util.rx.scheduler.SchedulerUtils;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Completable;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;
    @Inject
    AppComponent appContext;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getPokemon() {
        checkViewAttached();
        getView().showProgress(true);
        Single.fromCallable(() -> CBCDatabase.getInstance(appContext.context()).changeEventDao().getAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        changeEvents -> {
                            getView().showProgress(false);
                            getView().showPokemon(changeEvents);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        }

                );
    }
}
