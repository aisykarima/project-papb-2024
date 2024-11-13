package ak.mobile.aaproject4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvLaporan;
    private LaporanAdapter laporanAdapter;
    private List<Laporan> laporanList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvLaporan = view.findViewById(R.id.rvLaporan);
        rvLaporan.setLayoutManager(new LinearLayoutManager(getContext()));

        laporanList = new ArrayList<>();
        populateLaporanList();  // Step 2: Populate data

        laporanAdapter = new LaporanAdapter(getContext(), laporanList);
        rvLaporan.setAdapter(laporanAdapter);

        return view;
    }

    // Step 2: Populate data
    private void populateLaporanList() {
        laporanList.add(new Laporan(R.drawable.dompet, "02/10/2024 15:46", "Dompet hilang di daerah mushola Jatim Park",
                "Kehilangan", "Jawa Timur Park 1", "Laporan telah terkirim dan diterima oleh pihak berwenang"));
        laporanList.add(new Laporan(R.drawable.pukul,"03/10/2024 12.03", "Penganiayaan anak di taman",
                "Kekerasan", "Taman Merjosari", "Lapaoran sedang diproses pihak berwajib"));
        laporanList.add(new Laporan(R.drawable.tabrak,"10/09/2024 10.29", "Tabrak lari di dekat UB",
                "Kecelakaan", "Jalan Veteran", "Lapaoran ditangani pihak berwajib"));
        laporanList.add(new Laporan(R.drawable.toilet,"24/08/2024 16.03", "Fasilitas toilet umum sangat tidak layak",
                "Vandalisme", "Kota Malang", "Lapaoran diterima"));
        laporanList.add(new Laporan(R.drawable.dompet, "02/10/2024 15:46", "Dompet hilang di daerah mushola Jatim Park",
                "Kehilangan", "Jawa Timur Park 1", "Laporan telah terkirim dan diterima oleh pihak berwenang"));
        laporanList.add(new Laporan(R.drawable.tabrak, "02/10/2024 15:46", "Dompet hilang di daerah mushola Jatim Park",
                "Kehilangan", "Jawa Timur Park 1", "Laporan telah terkirim dan diterima oleh pihak berwenang"));
        // Add more items as needed
    }
}