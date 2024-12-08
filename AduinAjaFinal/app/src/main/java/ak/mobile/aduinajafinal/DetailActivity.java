package ak.mobile.aduinajafinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private ImageView pic, back;
    private TextView title, kategori, lokasi, kronologi, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        pic = findViewById(R.id.pic);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        kategori = findViewById(R.id.kategori);
        lokasi = findViewById(R.id.lokasi);
        kronologi = findViewById(R.id.kronologi);
        detail = findViewById(R.id.detail);

        Intent intent = getIntent();
        if (intent != null) {
            String picName = intent.getStringExtra("pic");
            int imageResId = getResources().getIdentifier(picName, "drawable", getPackageName());
            pic.setImageResource(imageResId);

            title.setText(intent.getStringExtra("title"));
            kategori.setText(intent.getStringExtra("kategori"));
            lokasi.setText(intent.getStringExtra("lokasi"));
            kronologi.setText(intent.getStringExtra("kronologi"));
            detail.setText(intent.getStringExtra("detail"));
        }

        // Handle back button
        back.setOnClickListener(v -> onBackPressed());
    }
}
