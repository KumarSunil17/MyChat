package com.kumarsunil17.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllUsersActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView listUser;
    private DatabaseReference userRef;
    private UserAdapter m;
    private static AllUsersActivity inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        listUser = findViewById(R.id.container_allUsers);
        inst = this;
        userRef.keepSynced(true);

        FirebaseRecyclerOptions<UserData> option = new FirebaseRecyclerOptions.Builder<UserData>()
            .setQuery(userRef.orderByValue(),UserData.class)
            .build();

        m = new UserAdapter(option,getApplicationContext());

        listUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listUser.hasFixedSize();
        m.notifyDataSetChanged();
        listUser.setAdapter(m);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_profile:
                Intent i = new Intent(getApplicationContext(),UserProfileActivity.class);
                i.putExtra("frienduid",mAuth.getCurrentUser().getUid());
                startActivity(i);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        m.startListening();
    }
    public static AllUsersActivity getInstance(){
        return inst;
    }
}
