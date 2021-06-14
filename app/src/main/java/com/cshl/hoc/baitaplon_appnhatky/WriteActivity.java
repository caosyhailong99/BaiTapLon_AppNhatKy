package com.cshl.hoc.baitaplon_appnhatky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cshl.hoc.baitaplon_appnhatky.model.DiaryPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class WriteActivity extends AppCompatActivity {
    public final static int RESULT_OK = 2;
    private EditText oBaiViet;
    private Button nutLuuBai;
    private DatabaseReference dbRef;
    private EditText oTenBai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
        nutLuuBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                dbRef = database.getReference("diary");
                String tieuDe = oTenBai.getText().toString();
                String noiDung = oBaiViet.getText().toString();
                if(tieuDe.compareTo("") == 0) {
                    Toast.makeText(WriteActivity.this, "Chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
                }
                else if(tieuDe.contains("\n")) {
                    Toast.makeText(WriteActivity.this, "Tiêu đề không được xuống dòng", Toast.LENGTH_SHORT).show();
                }
                else if(noiDung.compareTo("") == 0) {
                    Toast.makeText(WriteActivity.this, "Chưa viết nhật ký", Toast.LENGTH_SHORT).show();
                }
                else {
                    long createdDateTime = System.currentTimeMillis();
                    DiaryPage diaryPage = new DiaryPage(tieuDe, noiDung, createdDateTime);
                    dbRef.child(diaryPage.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            DiaryPage d = snapshot.getValue(DiaryPage.class);
                            if(d != null && d.getTitle().compareTo(diaryPage.getTitle()) == 0) {
                                Toast.makeText(WriteActivity.this, "Tên tiêu đề đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                dbRef.child(diaryPage.getTitle()).setValue(diaryPage);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
    public void initView() {
        oBaiViet = findViewById(R.id.oBaiViet);
        oTenBai = findViewById(R.id.oTenBai);
        nutLuuBai = findViewById(R.id.nutLuuBai);
    }
}