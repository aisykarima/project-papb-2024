package ak.mobile.aaproject6;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    public static final
    String FirebaseURL = "https://aaproj6-bc760-default-rtdb.asia-southeast1.firebasedatabase.app/";
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    LaporFragment laporFragment = new LaporFragment();
    ProfilFragment profilFragment = new ProfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create HomeFragment and pass Firebase URL
        homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("firebase_url", FirebaseURL);
        homeFragment.setArguments(bundle);

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
}