package mx.itson.db;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder> {

    private List<Mascota> list;
    private Activity activity;
    private OnItemLongClickListener callback;

    public MascotaAdapter(List<Mascota> list, Activity activity, OnItemLongClickListener callback) {
        this.list = list;
        this.activity = activity;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mascota mascota = list.get(position);
        holder.tvNombreMascota.setText( mascota.getNombre() );
        holder.tvTipoMascota.setText( mascota.getTipo() );

        activity.registerForContextMenu( holder.llMainContainer );
        holder.llMainContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(callback != null) {
                    callback.onSelectionLongClick(mascota, position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreMascota, tvTipoMascota;
        LinearLayout llMainContainer;

        public ViewHolder(View view) {
            super(view);
            tvNombreMascota = view.findViewById(R.id.tvNombreMascota);
            tvTipoMascota = view.findViewById(R.id.tvTipoMascota);
            llMainContainer = view.findViewById(R.id.llMainContainer);
        }
    }

    public List<Mascota> getList() {
        return list;
    }

    public void setList(List<Mascota> list) {
        this.list = list;
    }

    public interface OnItemLongClickListener {
        void onSelectionLongClick(Mascota mascota, int position);
    }
}
