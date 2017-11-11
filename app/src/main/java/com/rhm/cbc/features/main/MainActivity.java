package com.rhm.cbc.features.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.rhm.cbc.R;
import com.rhm.cbc.data.model.ChangeGroup;
import com.rhm.cbc.features.base.BaseActivity;
import com.rhm.cbc.features.common.ErrorView;
import com.rhm.cbc.features.detail.SecondaryActivity;
import com.rhm.cbc.injection.component.ActivityComponent;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {

    @Inject
    ChangeGroupAdapter changeGroupAdapter;
    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView changeGroupRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getChangeGroups(true));

        changeGroupRecycler.setLayoutManager(new LinearLayoutManager(this));
        changeGroupRecycler.setAdapter(changeGroupAdapter);
        changeGroupRecycler.setHasFixedSize(true);
        attachGroupClicked();
        errorView.setErrorListener(this);

        mainPresenter.getChangeGroups(true);
    }


    private void attachGroupClicked() {
        Disposable disposable =
                changeGroupAdapter
                        .getGroupClick()
                        .subscribe(
                                changeGroup ->
                                        startActivity(SecondaryActivity.getStartIntent(this, changeGroup)),
                                throwable -> {
                                    Timber.e(throwable, "Change group click failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
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
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showGroups(List<ChangeGroup> groups) {
        changeGroupAdapter.setGroup(groups);
        changeGroupRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (changeGroupRecycler.getVisibility() == View.VISIBLE
                    && changeGroupAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                changeGroupRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public ChangeGroupAdapter getAdapter() {
        return changeGroupAdapter;
    }

    @Override
    public void showError(Throwable error) {
        changeGroupRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the ChangeGroups");
    }

    @Override
    public void onReloadData() {
        mainPresenter.getChangeGroups(true);
    }
}
