package com.rhm.cbc.features.detail;

import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.ChangeGroup;
import com.rhm.cbc.features.base.MvpView;

import java.util.List;

public interface SecondaryMvpView extends MvpView {

    void showEvents(List<ChangeEvent> changeEvents);

    void showProgress(boolean show);

    void showError(Throwable error);

    int getYearMonthDay();
}
