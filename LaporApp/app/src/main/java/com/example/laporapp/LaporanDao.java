package com.example.laporapp;

import java.util.List;

public interface LaporanDao {
    void tambahLaporan(Laporan laporan); // Create
    List<Laporan> ambilSemuaLaporan(); // Read
    void perbaruiLaporan(Laporan laporan); // Update
    void hapusLaporan(int id); // Delete
}
