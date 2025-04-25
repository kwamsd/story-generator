package com.example.storygenerator;

import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MotsClesAdapter extends RecyclerView.Adapter<MotsClesAdapter.ViewHolder> {
    private final List<String> items;
    private final List<String> selected = new ArrayList<>();

    public MotsClesAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckBox cb = new CheckBox(parent.getContext());
        cb.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cb.setTextColor(parent.getContext().getResources().getColor(R.color.white));
        return new ViewHolder(cb);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mot = items.get(position);
        holder.checkBox.setText(mot);
        holder.checkBox.setChecked(selected.contains(mot));
        holder.checkBox.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                if (!selected.contains(mot)) selected.add(mot);
            } else {
                selected.remove(mot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getChoix() {
        return new ArrayList<>(selected);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ViewHolder(@NonNull CheckBox itemView) {
            super(itemView);
            checkBox = itemView;
        }
    }
}
