package com.kumarsunil17.mychat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalChatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText msgText;
    private Button backBtn;
    private TextView appName;
    private CircleImageView appDp;
    private DatabaseReference myRef,frndRef,msgRef;
    private RecyclerView list;
    private FloatingActionButton sendMessage;
    private String myUid,frndUid;
    private FirebaseRecyclerAdapter<MessageData,MessageViewHolder> f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_chat_activity);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_app_bar);

        View customApp = getSupportActionBar().getCustomView();
        backBtn = customApp.findViewById(R.id.app_bar_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appDp = customApp.findViewById(R.id.app_bar_dp);
        appName = customApp.findViewById(R.id.app_bar_name);

        list = findViewById(R.id.msg_list);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(PersonalChatActivity.this,LinearLayoutManager.VERTICAL,false));
        msgText = findViewById(R.id.msg_text);
        mAuth = FirebaseAuth.getInstance();
        sendMessage = findViewById(R.id.send_message);
        frndUid = getIntent().getExtras().getString("uid");

        appName.setText(frndUid);
        appDp.setBackgroundColor(Color.RED);
        appName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
            }
        });
        myUid = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("chat").child(myUid).child(frndUid);
        frndRef = FirebaseDatabase.getInstance().getReference().child("chat").child(frndUid).child(myUid);
        msgRef = FirebaseDatabase.getInstance().getReference().child("message");


        FirebaseRecyclerOptions<MessageData> options = new FirebaseRecyclerOptions.Builder<MessageData>().setQuery(myRef,MessageData.class).build();

        f = new FirebaseRecyclerAdapter<MessageData,MessageViewHolder>(options){

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(PersonalChatActivity.this).inflate(R.layout.message_row,parent,false);
                return new MessageViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MessageViewHolder holder, int position, @NonNull MessageData model) {
                String id = model.getId();
                DatabaseReference db = msgRef.child(id);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.child("text").getValue().toString();
                        String time = dataSnapshot.child("time").getValue().toString();
                        String sender = dataSnapshot.child("sender").getValue().toString();
                        holder.setTvTime(time);
                        holder.setTvText(text);
                        holder.lin.setVisibility(View.VISIBLE);
                        if (sender.equals(myUid)){
                            holder.lin.setGravity(Gravity.END);
                        }else{
                            holder.lin.setGravity(Gravity.START);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        list.setAdapter(f);

        msgText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    sendMessage.setEnabled(false);
                }else{
                    sendMessage.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgText.getText().toString().trim();
                msgText.setText("");
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                String time = s.format(d);
                Map<String,Object> m = new HashMap<>();
                m.put("text",msg);
                m.put("time",time);
                m.put("sender",myUid);
                final String key = msgRef.push().getKey();
                msgRef.child(key).updateChildren(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            myRef.push().child("id").setValue(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        frndRef.push().child("id").setValue(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(PersonalChatActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(PersonalChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(PersonalChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(PersonalChatActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }
}
