package com.rhm.cbc.features.main;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.ChangeGroup;
import com.rhm.cbc.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showGroups(List<ChangeGroup> groups);

    void showProgress(boolean show);

    ChangeGroupAdapter getAdapter();

    void showError(Throwable error);
}
