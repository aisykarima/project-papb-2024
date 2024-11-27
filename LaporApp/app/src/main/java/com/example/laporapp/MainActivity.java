package com.example.laporapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final
    String FirebaseURL =
            "https://lapor-bcf5a-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private EditText tiJenis, tiKategori, tiLokasi, tiKronologi, tiInformasi;
    private Button btKirim;
    private DatabaseReference appDb;
    private List<Laporan> laporanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ganti dengan nama layout Anda jika berbeda

        // Inisialisasi Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        appDb = database.getReference("laporan");

        tiJenis = findViewById(R.id.tiJenis);
        tiKategori = findViewById(R.id.tiKategori);
        tiLokasi = findViewById(R.id.tiLokasi);
        tiKronologi = findViewById(R.id.tiKronologi);
        tiInformasi = findViewById(R.id.tiInformasi);
        btKirim = findViewById(R.id.btKirim);

        laporanList = new ArrayList<>();

        btKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirimLaporan();
            }
        });

        // Mengambil semua laporan dari Firebase
        appDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                laporanList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Laporan laporan = dataSnapshot.getValue(Laporan.class);
                    if (laporan != null) {
                        laporanList.add(laporan);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kirimLaporan() {
        String jenis = tiJenis.getText().toString().trim();
        String kategori = tiKategori.getText().toString().trim();
        String lokasi = tiLokasi.getText().toString().trim();
        String kronologi = tiKronologi.getText().toString().trim();
        String informasi = tiInformasi.getText().toString().trim();

        // Validasi input
        if (jenis.isEmpty() || kategori.isEmpty() || lokasi.isEmpty() || kronologi.isEmpty() || informasi.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek laporan
        String id = appDb.push().getKey(); // Generate unique key
        Laporan laporan = new Laporan(id, jenis, kategori, lokasi, kronologi); // Gunakan id sebagai String

        // Menyimpan laporan ke Firebase
        if (id != null) {
            appDb.child(id).setValue(laporan)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MainActivity.this, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show();
                        // Kosongkan field setelah pengiriman
                        tiJenis.setText("");
                        tiKategori.setText("");
                        tiLokasi.setText("");
                        tiKronologi.setText("");
                        tiInformasi.setText("");
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Gagal mengirim laporan", Toast.LENGTH_SHORT).show());
        }
    }
}