package ak.mobile.aaproject4;

public class Laporan {
    public int pic;
    public String time;
    public String title;
    public String kategori;
    public String lokasi;
    public String status;

    public Laporan(int pic, String time, String title, String kategori, String lokasi, String status) {
        this.pic = pic;
        this.time = time;
        this.title = title;
        this.kategori = kategori;
        this.lokasi = lokasi;
        this.status = status;
    }
}
