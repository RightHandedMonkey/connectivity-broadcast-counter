package com.rhm.cbc.features.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhm.cbc.R;
import com.rhm.cbc.data.model.ChangeEvent;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeEventAdapter extends RecyclerView.Adapter<ChangeEventAdapter.ChangeEventViewHolder> {

    private List<ChangeEvent> changeEventList;

    @Inject
    ChangeEventAdapter() {
        changeEventList = Collections.emptyList();
    }

    public void setChangeEvents(List<ChangeEvent> ce) {
        this.changeEventList = ce;
        notifyDataSetChanged();
    }

    @Override
    public ChangeEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_change_event, parent, false);
        return new ChangeEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChangeEventViewHolder holder, int position) {
        ChangeEvent ce = this.changeEventList.get(position);
        holder.onBind(ce);
    }

    @Override
    public int getItemCount() {
        return changeEventList.size();
    }

    class ChangeEventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView nameText;
        @BindView(R.id.text_ssid)
        TextView ssidText;
        @BindView(R.id.text_detailed_state)
        TextView detailedStateText;
        @BindView(R.id.text_event_time)
        TextView eventTimeText;
        @BindView(R.id.text_complete_msg)
        TextView completeMsgText;

        private ChangeEvent changeEvent;

        ChangeEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBind(ChangeEvent changeEvent) {
            this.changeEvent = changeEvent;
            nameText.setText("TYPE: " + changeEvent.getTypeName());
            ssidText.setText("SSID: " + changeEvent.getSsid());
            detailedStateText.setText("STATE: " + changeEvent.getDetailedState());
            eventTimeText.setText("TIME: " + changeEvent.getEventTime());
            completeMsgText.setText(changeEvent.getCompleteMsg());
        }
    }
}
