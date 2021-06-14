package com.cshl.hoc.baitaplon_appnhatky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText oNhapTK, oNhapMK;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oNhapTK = findViewById(R.id.oNhapTK);
        oNhapMK = findViewById(R.id.oNhapMK);
        Button nutDangNhap = findViewById(R.id.nutDangNhap);

        nutDangNhap.setOnClickListener(v -> {
            String email = oNhapTK.getText().toString();
            System.out.println("Email: " + email);
            String password = oNhapMK.getText().toString();
            System.out.println("Password: " + password);
            if(email.compareTo("") == 0) {
                Toast.makeText(MainActivity.this,"Email không được để trống", Toast.LENGTH_SHORT).show();
            }
            else if(password.compareTo("") == 0) {
                Toast.makeText(MainActivity.this,"Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            }
            else {
                auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, DiaryListActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}