package com.freedman.parsingtool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freedman.parsingtool.tables.ParsedEntries;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ItemViewHolder> {

    private List<ParsedEntries> parsedEntries;


    public DataAdapter(List<ParsedEntries> parsedEntries) {
        this.parsedEntries = parsedEntries;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_info_database, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        ParsedEntries currentItem = parsedEntries.get(position);
        // Bind data to the view
        holder.EditText.setText(currentItem.getTableName());
    }

    @Override
    public int getItemCount() {
        return parsedEntries.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public EditText EditText;

        public ItemViewHolder(@NonNull View parsedEntries) {
            super(parsedEntries);
            EditText = parsedEntries.findViewById(R.id.edit_text_file_name);
        }
    }
}
