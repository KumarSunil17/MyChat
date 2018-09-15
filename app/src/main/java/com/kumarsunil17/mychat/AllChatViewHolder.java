package com.kumarsunil17.mychat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllChatViewHolder extends RecyclerView.ViewHolder {
    private TextView chatUserName, chatUserTime;
    private CircleImageView dp;
    View v;

    public AllChatViewHolder(View itemView) {
        super(itemView);
        v = itemView;
        chatUserName = itemView.findViewById(R.id.all_chat_name);
        chatUserTime = itemView.findViewById(R.id.all_chat_time);
        dp = itemView.findViewById(R.id.all_chat_row_dp);
    }

    public void setChatUserName(String name){
        this.chatUserName.setText(name);
    }

    public CircleImageView getDp() {
        return dp;
    }
}
