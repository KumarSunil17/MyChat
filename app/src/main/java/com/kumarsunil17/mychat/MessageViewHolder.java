package com.kumarsunil17.mychat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView tvText,tvTime;
    public LinearLayout lin;

    public MessageViewHolder(View itemView) {
        super(itemView);
        tvText = itemView.findViewById(R.id.msg_text);
        lin  =itemView.findViewById(R.id.msg_row_layout);
        tvTime = itemView.findViewById(R.id.msg_time);
    }

    public void setTvText(String tvText) {
        this.tvText.setText(tvText);
    }

    public void setTvTime(String tvTime) {
        this.tvTime.setText(tvTime);
    }
}
