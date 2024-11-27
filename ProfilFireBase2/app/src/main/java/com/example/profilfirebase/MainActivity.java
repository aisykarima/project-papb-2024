package com.example.profilfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.profilfirebase.LoginActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone;
    private Button btnSave;
    private DatabaseReference databaseReference;
    private EditText etName;
    private EditText etEmail; // Deklarasi variabel untuk EditText email



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Views
        editTextName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        editTextPhone = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.buttonSave);  // Pastikan ID sesuai dengan XML




        // Inisialisasi Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void saveUserData() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan data ke Firebase
        String userId = databaseReference.push().getKey();
        User user = new User(name, email, phone);
        databaseReference.child(userId).setValue(user);

        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }

    // User Class
    public static class User {
        public String name, email, phone;

        public User() {
        }

        public User(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }
}
