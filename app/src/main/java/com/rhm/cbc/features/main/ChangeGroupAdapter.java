package com.rhm.cbc.features.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.rhm.cbc.R;
import com.rhm.cbc.data.model.ChangeGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class ChangeGroupAdapter extends RecyclerView.Adapter<ChangeGroupAdapter.GroupViewHolder> {

    private List<ChangeGroup> groupList;
    private Subject<ChangeGroup> groupClickSubject;

    @Inject
    ChangeGroupAdapter() {
        groupClickSubject = PublishSubject.create();
        groupList = Collections.emptyList();
    }

    public void setGroup(List<ChangeGroup> group) {
        this.groupList = group;
        notifyDataSetChanged();
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        ChangeGroup group = this.groupList.get(position);
        holder.onBind(group);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    Observable<ChangeGroup> getGroupClick() {
        return groupClickSubject;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_date)
        TextView nameText;
        @BindView(R.id.text_count)
        TextView countText;

        private ChangeGroup group;

        GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> groupClickSubject.onNext(group));
        }

        void onBind(ChangeGroup group) {
            this.group = group;
            nameText.setText(group.getFormattedDate());
            countText.setText("Count: "+group.count);
        }
    }
}
