package com.example.profilfirebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private ImageView imageViewProfile;
    private EditText etName, etEmail, editTextPhone;
    private Button buttonSave, buttonGanti;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi Firebase Auth dan Database
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // Jika pengguna belum login, kembali ke LoginActivity
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        // Inisialisasi Views
        textViewTitle = findViewById(R.id.textView);
        imageViewProfile = findViewById(R.id.imageView);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSave = findViewById(R.id.buttonSave);
        buttonGanti = findViewById(R.id.buttonGanti);

        // Memuat data profil
        loadUserProfile();

        // Klik tombol Save untuk menyimpan data profil
        buttonSave.setOnClickListener(v -> saveUserProfile());

        // Klik tombol Ganti untuk mengganti foto profil
        buttonGanti.setOnClickListener(v -> openGallery());
    }

    private void loadUserProfile() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Ambil data dari Firebase
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String profileImageUrl = snapshot.child("profileImage").getValue(String.class);

                    // Set data ke views
                    etName.setText(name);
                    etEmail.setText(email);
                    editTextPhone.setText(phone);

                    // Muat gambar menggunakan Picasso (atau library gambar lain)
                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        Picasso.get().load(profileImageUrl).into(imageViewProfile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserProfile() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone is required");
            return;
        }

        // Simpan data ke Firebase
        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put("name", name);
        profileMap.put("email", email);
        profileMap.put("phone", phone);

        userRef.updateChildren(profileMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        // Buka galeri untuk mengganti foto profil
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == Activity.RESULT_OK && data != null) {
            // Ambil URI gambar dari galeri
            imageViewProfile.setImageURI(data.getData());
            // (Implementasikan logika untuk mengunggah gambar ke Firebase Storage)
        }
    }
}
