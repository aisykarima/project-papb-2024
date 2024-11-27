package ak.mobile.aaproject6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvLaporan;
    private LaporanAdapter laporanAdapter;
    private List<Laporan> laporanList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvLaporan = view.findViewById(R.id.rvLaporan);
        rvLaporan.setLayoutManager(new LinearLayoutManager(getContext()));

        laporanList = new ArrayList<>();
        laporanAdapter = new LaporanAdapter(getContext(), laporanList);
        rvLaporan.setAdapter(laporanAdapter);

        // Get Firebase URL from Bundle
        String firebaseUrl = null;
        if (getArguments() != null) {
            firebaseUrl = getArguments().getString("firebase_url");
        }

        // Initialize Firebase Database reference using the URL
        if (firebaseUrl != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance(firebaseUrl);
            databaseReference = database.getReference("laporan");
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("laporan");
        }

        fetchLaporanFromFirebase();

        return view;
    }

    private void fetchLaporanFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                laporanList.clear(); // Clear list to avoid duplication
                for (DataSnapshot data : snapshot.getChildren()) {
                    Laporan laporan = data.getValue(Laporan.class);
                    if (laporan != null) {
                        laporanList.add(laporan);
                    }
                }
                laporanAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}