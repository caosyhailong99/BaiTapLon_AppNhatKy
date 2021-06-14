package com.cshl.hoc.baitaplon_appnhatky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cshl.hoc.baitaplon_appnhatky.model.DiaryPage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private TextView oTenBai;
    private EditText oVietBai;
    private Button nutLuuBai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        Intent intent = getIntent();
        DiaryPage diaryPage = (DiaryPage) intent.getSerializableExtra("diaryPage");
        String title = diaryPage.getTitle();
        oTenBai.setText(title);
        String content = diaryPage.getContent();
        oVietBai.setText(content);
        nutLuuBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedcontent = oVietBai.getText().toString();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("diary");
                Map<String, Object> map = new HashMap<>();
                map.put("/" + title + "/content/", updatedcontent);
                dbRef.updateChildren(map);
                finish();
            }
        });
    }

    public void initView() {
        oTenBai = findViewById(R.id.oTenBai);
        oVietBai = findViewById(R.id.oBaiViet);
        nutLuuBai = findViewById(R.id.nutLuuBai);
    }
}