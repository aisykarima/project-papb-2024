package com.example.laporapp;

public class Laporan {
    private String id;
    private String jenis;
    private String kategori;
    private String lokasi;
    private String kronologi;

    // Constructor
    public Laporan(String id, String jenis, String kategori, String lokasi, String kronologi) {
        this.id = id;
        this.jenis = jenis;
        this.kategori = kategori;
        this.lokasi = lokasi;
        this.kronologi = kronologi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKronologi() {
        return kronologi;
    }

    public void setKronologi(String kronologi) {
        this.kronologi = kronologi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    // Getters and Setters
//    public int getId() { return id; }
//    public String getJenis() { return jenis; }
//    public String getKategori() { return kategori; }
//    public String getLokasi() { return lokasi; }
//    public String getKronologi() { return kronologi; }
//
//    public void setJenis(String jenis) { this.jenis = jenis; }
//    public void setKategori(String kategori) { this.kategori = kategori; }
//    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
//    public void setKronologi(String kronologi) { this.kronologi = kronologi; }
}
