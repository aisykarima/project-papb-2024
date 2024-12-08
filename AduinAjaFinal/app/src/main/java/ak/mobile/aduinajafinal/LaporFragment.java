package ak.mobile.aduinajafinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LaporFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private EditText titleEditText, kategoriEditText, lokasiEditText, tanggalEditText, kronologiEditText, detailEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lapor, container, false);

        // Ambil Firebase Reference dari MainActivity
        databaseReference = ((MainActivity) requireActivity()).getDatabaseReference().child("Laporan");

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        titleEditText = view.findViewById(R.id.etTitle);
        kategoriEditText = view.findViewById(R.id.etKategori);
        lokasiEditText = view.findViewById(R.id.etLokasi);
        tanggalEditText = view.findViewById(R.id.etWaktu);
        kronologiEditText = view.findViewById(R.id.etKronologi);
        detailEditText = view.findViewById(R.id.etDetail);
        submitButton = view.findViewById(R.id.btKirim);

        submitButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String kategori = kategoriEditText.getText().toString().trim();
            String lokasi = lokasiEditText.getText().toString().trim();
            String tanggal = tanggalEditText.getText().toString().trim();
            String kronologi = kronologiEditText.getText().toString().trim();
            String detail = detailEditText.getText().toString().trim();

            if (!title.isEmpty() && !kategori.isEmpty() && !lokasi.isEmpty()) {
                Laporan newLaporan = new Laporan();
                newLaporan.setPic("dompet");
                newLaporan.setTime(tanggal);
                newLaporan.setTitle(title);
                newLaporan.setKategori(kategori);
                newLaporan.setLokasi(lokasi);
                newLaporan.setStatus("Laporan baru diterima");
                newLaporan.setKronologi(kronologi);
                newLaporan.setDetail(detail);

                // Tambahkan laporan ke Firebase
                String laporanId = databaseReference.push().getKey(); // Generate unique key
                if (laporanId != null) {
                    databaseReference.child(laporanId).setValue(newLaporan)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Laporan berhasil disimpan ke Firebase", Toast.LENGTH_SHORT).show();
                                clearInputFields();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Gagal menyimpan laporan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                Toast.makeText(getContext(), "Harap isi semua data yang diperlukan!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void clearInputFields() {
        titleEditText.setText("");
        kategoriEditText.setText("");
        lokasiEditText.setText("");
        tanggalEditText.setText("");
        kronologiEditText.setText("");
        detailEditText.setText("");
    }
}