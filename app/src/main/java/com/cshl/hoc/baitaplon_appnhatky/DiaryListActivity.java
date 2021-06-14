package com.cshl.hoc.baitaplon_appnhatky;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cshl.hoc.baitaplon_appnhatky.model.DiaryPage;
import com.cshl.hoc.baitaplon_appnhatky.model.DiaryPageAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class DiaryListActivity extends AppCompatActivity {
    public final static int REQUEST_CODE = 1;
    public TextView emptyView;
    private RecyclerView recyclerView;
    private Button nutDangXuat;
    private FloatingActionButton fabViet;
    private DiaryPageAdapter adapter;
    private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        initView();
        nutDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        fabViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVietBai = new Intent(DiaryListActivity.this, WriteActivity.class);
                startActivity(intentVietBai);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbRef = FirebaseDatabase.getInstance().getReference("diary");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int numPages = 0;
                for(DataSnapshot ds: snapshot.getChildren()) {
                    numPages++;
                }
                if(numPages == 0) {
                    emptyView.setText("Chưa có bài viết nào");
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<DiaryPage> options = new FirebaseRecyclerOptions.Builder<DiaryPage>()
                .setQuery(dbRef, DiaryPage.class)
                .build();
        adapter = new DiaryPageAdapter(options, DiaryListActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        emptyView = findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.recView);
        nutDangXuat = findViewById(R.id.nutDangXuat);
        fabViet = findViewById(R.id.fabViet);
    }

    @Override
    protected void onStart() {
        super.onStart();
        emptyView.setText("Đang tải bài viết...");
        adapter.startListening();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int numPages = 0;
                for(DataSnapshot ds: snapshot.getChildren()) {
                    numPages++;
                }
                if(numPages == 0) {
                    emptyView.setText("Chưa có bài viết nào");
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}