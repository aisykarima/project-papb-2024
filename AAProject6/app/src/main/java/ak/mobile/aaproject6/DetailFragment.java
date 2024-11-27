package ak.mobile.aaproject6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private ImageView pic, back;
    private TextView title, kategori, lokasi, kronologi, detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Bind views
        pic = view.findViewById(R.id.pic);
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        kategori = view.findViewById(R.id.kategori);
        lokasi = view.findViewById(R.id.lokasi);

        // Get data from bundle
        Bundle args = getArguments();
        if (args != null) {
            String imageName = args.getString("pic");
            int imageResId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
            pic.setImageResource(imageResId);
            
            title.setText(args.getString("title"));
            kategori.setText(args.getString("kategori"));
            lokasi.setText(args.getString("lokasi"));
        }

        // Handle back button
        back.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
