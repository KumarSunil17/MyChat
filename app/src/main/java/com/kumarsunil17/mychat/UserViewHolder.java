package com.kumarsunil17.mychat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName,tvEmail;
    View v;
    private CircleImageView dp;
    CardView myMain;

    public UserViewHolder(View itemView) {
        super(itemView);
        v = itemView;
        tvName = itemView.findViewById(R.id.row_name);
        tvEmail = itemView.findViewById(R.id.row_email);
        myMain = itemView.findViewById(R.id.main_row);
        dp = itemView.findViewById(R.id.row_dp);
    }

    public CircleImageView setDp() {
        return dp;
    }

    public void setTvName(String tvName) {
        this.tvName.setText(tvName);
    }

    public void setTvEmail(String tvEmail) {
        this.tvEmail.setText(tvEmail);
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvEmail() {
        return tvEmail;
    }


}
