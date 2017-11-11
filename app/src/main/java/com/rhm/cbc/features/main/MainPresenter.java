package com.rhm.cbc.features.main;

import com.rhm.cbc.data.CBCDatabase;
import com.rhm.cbc.features.base.BasePresenter;
import com.rhm.cbc.injection.ConfigPersistent;
import com.rhm.cbc.injection.component.AppComponent;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    AppComponent appContext;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        attachRefreshDisposable();
    }

    private void attachRefreshDisposable() {
        Disposable d = CBCDatabase.getInstance(appContext.context()).changeEventDao().getAllGroupsFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changeGroup -> {
                            //just refresh the list
                            getChangeGroups(false);
                        }
                );
        addDisposable(d);
    }

    public void getChangeGroups(boolean show_progress) {
        checkViewAttached();
        if (show_progress) {
            getView().showProgress(true);
        }
        Single.fromCallable(() -> CBCDatabase.getInstance(appContext.context()).changeEventDao().getAllGroups())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        changeEvents -> {
                            getView().showProgress(false);
                            getView().showGroups(changeEvents);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        }

                );
    }


}
