package ak.mobile.aduinajafinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail, editTextPhone;
    private Button buttonSave;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSave = findViewById(R.id.buttonSave);
        ImageView btBack = findViewById(R.id.btBack);

        // Inisialisasi DatabaseReference
        databaseReference = FirebaseDatabase.getInstance("https://aduinaja-8596f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("users")
                .child("profile");

        // Load data profil dari Firebase
        loadUserProfile();

        buttonSave.setOnClickListener(v -> saveUserProfile());

        btBack.setOnClickListener(v -> finish());
    }

    private void loadUserProfile() {
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                User user = task.getResult().getValue(User.class);
                if (user != null) {
                    // Isi data ke EditText
                    etName.setText(user.getName());
                    etEmail.setText(user.getEmail());
                    editTextPhone.setText(user.getPhone());
                }
            } else {
                Toast.makeText(this, "Gagal memuat profil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Menyimpan data profil ke Firebase
    private void saveUserProfile() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Nama tidak boleh kosong");
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email tidak boleh kosong");
            return;
        }
        if (phone.isEmpty()) {
            editTextPhone.setError("Nomor telepon tidak boleh kosong");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        // Simpan ke Firebase
        databaseReference.setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show());
    }
}