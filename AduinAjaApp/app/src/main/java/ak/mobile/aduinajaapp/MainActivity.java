package ak.mobile.aduinajaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvLaporan;
    private LaporanAdapter laporanAdapter;
    private List<Laporan> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.rvLaporan = this.findViewById(R.id.rvLaporan);

        List<Laporan> data = new ArrayList<>();
        data.add(new Laporan
                (R.drawable.pukul,"03/10/2024 12.03", "Penganiayaan anak di taman", "Kekerasan", "Taman Merjosari", "Lapaoran sedang diproses pihak berwajib"));
        data.add(new Laporan
                (R.drawable.tabrak,"10/09/2024 10.29", "Tabrak lari di dekat UB", "Kecelakaan", "Jalan Veteran", "Lapaoran ditangani pihak berwajib"));
        data.add(new Laporan
                (R.drawable.toilet,"24/08/2024 16.03", "Fasilitas toilet umum sangat tidak layak", "Vandalisme", "Kota Malang", "Lapaoran diterima"));
        data.add(new Laporan
                (R.drawable.dompet,"08/05/2024 22.23", "Dompet hilang di mushola", "Kehilangan", "Jawa Timur 2", "Lapaoran ditinjau"));
        this.data = data;

        this.laporanAdapter = new LaporanAdapter(this, data);
        this.rvLaporan.setAdapter(this.laporanAdapter);
        this.rvLaporan.setLayoutManager(
                new LinearLayoutManager(this)
        );
        this.laporanAdapter.setOnItemClickListener(new LaporanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Laporan laporan) {
                // Tampilkan Toast dengan informasi yang diambil dari item yang diklik
                Toast.makeText(MainActivity.this, "" + laporan.title, Toast.LENGTH_SHORT).show();
            }
        });
    }
}