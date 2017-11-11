package com.rhm.cbc.features.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rhm.cbc.R;
import com.rhm.cbc.data.model.ChangeEvent;
import com.rhm.cbc.data.model.ChangeGroup;
import com.rhm.cbc.features.base.BaseActivity;
import com.rhm.cbc.features.common.ErrorView;
import com.rhm.cbc.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class SecondaryActivity extends BaseActivity implements SecondaryMvpView, ErrorView.ErrorListener {

    private int yearMonthDay=0;

    @Inject
    ChangeEventAdapter changeEventAdapter;
    @Inject
    SecondaryPresenter secondaryPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView changeEventRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.yearMonthDay = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ChangeGroup cg = (ChangeGroup) extras.get(ChangeGroup.class.getName());
            if (cg instanceof ChangeGroup) {
                this.yearMonthDay = cg.yearMonthDay;
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(() -> secondaryPresenter.getChangeEvents(true));

        changeEventRecycler.setLayoutManager(new LinearLayoutManager(this));
        changeEventRecycler.setAdapter(changeEventAdapter);
        errorView.setErrorListener(this);

        secondaryPresenter.getChangeEventsByDay(yearMonthDay, true);
        secondaryPresenter.attachRefreshDisposable(yearMonthDay);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        secondaryPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        secondaryPresenter.detachView();
    }

    @Override
    public void showEvents(List<ChangeEvent> ce) {
        changeEventAdapter.setChangeEvents(ce);
        changeEventRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (changeEventRecycler.getVisibility() == View.VISIBLE
                    && changeEventAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                changeEventRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        changeEventRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the ChangeEvents");
    }

    @Override
    public int getYearMonthDay() {
        return yearMonthDay;
    }

    @Override
    public void onReloadData() {
        secondaryPresenter.getChangeEventsByDay(yearMonthDay, true);
    }

    public static Intent getStartIntent(Context context, ChangeGroup changeGroup) {
        Intent intent = new Intent(context, SecondaryActivity.class);
        intent.putExtra(ChangeGroup.class.getName(), changeGroup);
        return intent;
    }
}
