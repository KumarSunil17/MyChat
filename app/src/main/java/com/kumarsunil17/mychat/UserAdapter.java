package com.kumarsunil17.mychat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class UserAdapter extends FirebaseRecyclerAdapter<UserData,UserViewHolder> {
    private Context c;
    private FirebaseAuth mAuth;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserData> options, Context c) {
        super(options);
        this.c = c;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull final UserViewHolder holder, final int position, @NonNull final UserData model) {
        if (getRef(position).getKey().equals(mAuth.getCurrentUser().getUid())) {
            holder.myMain.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        }else {
            holder.setTvEmail(model.getEmail());
            holder.setTvName(model.getName());
            String url =  model.getDp();
            Picasso.with(AllUsersActivity.getInstance()).load(url).into(holder.setDp());
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c,PersonalChatActivity.class);
                    i.putExtra("uid",getRef(position).getKey());
                    AllUsersActivity.getInstance().startActivity(i);
                    AllUsersActivity.getInstance().finish();
                }
            });
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.user_list_row,parent,false);
        return new UserViewHolder(v);
    }
}
