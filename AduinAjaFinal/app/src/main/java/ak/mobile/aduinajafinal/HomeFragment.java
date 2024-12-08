package ak.mobile.aduinajafinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvLaporan;
    private LaporanAdapter laporanAdapter;
    private List<Laporan> laporanList = new ArrayList<>();
    private SharedViewModel sharedViewModel;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvLaporan = view.findViewById(R.id.rvLaporan);
        rvLaporan.setLayoutManager(new LinearLayoutManager(getContext()));

        laporanAdapter = new LaporanAdapter(getContext(), laporanList);
        rvLaporan.setAdapter(laporanAdapter);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getLaporanList().observe(getViewLifecycleOwner(), updatedList -> {
            laporanList.clear();
            laporanList.addAll(updatedList);
            laporanAdapter.notifyDataSetChanged();
        });

        // Ambil Firebase Reference dari MainActivity
        databaseReference = ((MainActivity) requireActivity()).getDatabaseReference().child("Laporan");

        // Ambil data dari Firebase
        fetchLaporanFromFirebase();

        return view;
    }

    private void fetchLaporanFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                laporanList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Laporan laporan = data.getValue(Laporan.class);
                    if (laporan != null) {
                        laporanList.add(laporan);
                    }
                }
                laporanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal memuat data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}