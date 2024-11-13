package ak.mobile.aduinajaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter {

    private final Context ctx;
    private final List<Laporan> data;
    private OnItemClickListener listener;

    // Interface untuk menangani klik pada item
    public interface OnItemClickListener {
        void onItemClick(Laporan laporan);
    }

    public LaporanAdapter(Context ctx, List<Laporan> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class LaporanVH extends RecyclerView.ViewHolder{

        private final TextView time;
        private final TextView title;
        private final TextView kategori;
        private final TextView lokasi;
        private final TextView status;
        private final ImageView pic;

        public LaporanVH(@NonNull View itemView) {
            super(itemView);
            this.pic = itemView.findViewById(R.id.pic);
            this.time = itemView.findViewById(R.id.time);
            this.title = itemView.findViewById(R.id.title);
            this.kategori = itemView.findViewById(R.id.kategori);
            this.lokasi = itemView.findViewById(R.id.lokasi);
            this.status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(data.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx)
                .inflate(R.layout.rowview, parent, false);
        RecyclerView.ViewHolder vh = new LaporanVH(rowView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Laporan l = this.data.get(position);
        LaporanVH vh = (LaporanVH) holder;
        vh.pic.setImageResource(l.pic);
        vh.time.setText(l.time);
        vh.title.setText(l.title);
        vh.kategori.setText(l.kategori);
        vh.lokasi.setText(l.lokasi);
        vh.status.setText(l.status);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
