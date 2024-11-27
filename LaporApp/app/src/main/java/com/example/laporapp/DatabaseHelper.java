package com.example.laporapp;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper implements LaporanDao {
    private static final String TAG = "DatabaseHelper";
    private DatabaseReference databaseReference;

    public DatabaseHelper() {
        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("laporan");
    }

    @Override
    public void tambahLaporan(Laporan laporan) {
        String id = databaseReference.push().getKey(); // Generate unique key
        if (id != null) {
            laporan.setId(id); // Set ID
            databaseReference.child(id).setValue(laporan)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Laporan berhasil ditambahkan"))
                    .addOnFailureListener(e -> Log.e(TAG, "Gagal menambahkan laporan", e));
        }
    }

    @Override
    public List<Laporan> ambilSemuaLaporan() {
        List<Laporan> laporanList = new ArrayList<>();
        // Anda dapat menggunakan ValueEventListener untuk mengambil data dari Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                laporanList.clear(); // Kosongkan list sebelum menambah data baru
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Laporan laporan = snapshot.getValue(Laporan.class);
                    if (laporan != null) {
                        laporanList.add(laporan);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Gagal mengambil data", databaseError.toException());
            }
        });
        return laporanList; // Anda mungkin ingin mengubah cara ini untuk mendapatkan data secara asinkron
    }

    @Override
    public void perbaruiLaporan(Laporan laporan) {
        // Implementasi untuk memperbarui laporan di Firebase
        databaseReference.child(String.valueOf(laporan.getId())).setValue(laporan)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Laporan berhasil diperbarui"))
                .addOnFailureListener(e -> Log.e(TAG, "Gagal memperbarui laporan", e));
    }

    @Override
    public void hapusLaporan(int id) {
        // Implementasi untuk menghapus laporan di Firebase
        databaseReference.child(String.valueOf(id)).removeValue()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Laporan berhasil dihapus"))
                .addOnFailureListener(e -> Log.e(TAG, "Gagal menghapus laporan", e));
    }
}