package com.rhm.cbc.features.detail;

import android.util.Log;

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
public class SecondaryPresenter extends BasePresenter<SecondaryMvpView> {

    @Inject
    AppComponent appContext;

    @Inject
    public SecondaryPresenter() {
    }

    @Override
    public void attachView(SecondaryMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void attachRefreshDisposable(int yearMonthDay) {
        Log.d("SAMB", "Creating refresh disposable with date ref '"+yearMonthDay+"'");
        Disposable d;
        if (yearMonthDay > 0) {
            d = CBCDatabase.getInstance(appContext.context()).changeEventDao().getEventsByYearMonthDayFlowable(yearMonthDay)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(changeEvent -> {
                                getChangeEventsByDay(yearMonthDay, false);
//                            getView().getAdapter().addItem(changeGroup);
                                Log.d("SAMB", "New object detected and added to adapter");
                                Log.d("SAMB", "Object: " + changeEvent);
                            }
                    );
        } else {
            d = CBCDatabase.getInstance(appContext.context()).changeEventDao().getAllFlowable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(changeEvent -> {
                                getChangeEvents(false);
//                            getView().getAdapter().addItem(changeGroup);
                                Log.d("SAMB", "New object detected and added to adapter");
                                Log.d("SAMB", "Object: " + changeEvent);
                            }
                    );
        }
        addDisposable(d);
    }

    public void getChangeEvents(boolean show_progress) {
        checkViewAttached();
        if (show_progress) {
            getView().showProgress(true);
        }
        Single.fromCallable(() -> {
            Log.d("SAMB", "Getting all events");
            return CBCDatabase.getInstance(appContext.context()).changeEventDao().getAll();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        changeEvents -> {
                            getView().showProgress(false);
                            getView().showEvents(changeEvents);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        }

                );
    }

    public void getChangeEventsByDay(int yearMonthDay, boolean show_progress) {
        checkViewAttached();
        if (show_progress) {
            getView().showProgress(true);
        }
        Single.fromCallable(() -> {
            if (yearMonthDay <= 0) {
                Log.d("SAMB", "Tried to get events by '"+yearMonthDay+"', but getting all instead");
                return CBCDatabase.getInstance(appContext.context()).changeEventDao().getAll();
            } else {
                Log.d("SAMB", "Getting events by '"+yearMonthDay+"'");
                return CBCDatabase.getInstance(appContext.context()).changeEventDao().getEventsByYearMonthDay(yearMonthDay);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        changeEvents -> {
                            getView().showProgress(false);
                            getView().showEvents(changeEvents);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        }

                );
    }

}
