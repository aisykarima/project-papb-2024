package ak.mobile.aduinajafinal;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final
    String FirebaseURL = "https://aduinaja-8596f-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private DatabaseReference databaseReference;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    LaporFragment laporFragment = new LaporFragment();
    ProfilFragment profilFragment = new ProfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance(FirebaseURL).getReference();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.lapor) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, laporFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.profil) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profilFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}