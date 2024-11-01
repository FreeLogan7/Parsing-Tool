package com.freedman.parsingtool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freedman.parsingtool.R;
import com.freedman.parsingtool.tables.ParsedEntries;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ItemViewHolder> {

    public List<ParsedEntries> parsedEntries;
    public static sendMeInfoListener listener;


    public void setParsedEntries(List<ParsedEntries> parsedEntries) {
        this.parsedEntries = parsedEntries;
    }

    public DataAdapter(sendMeInfoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_database_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        ParsedEntries currentItem = parsedEntries.get(position);
        holder.textViewTableName.setText(currentItem.getTableName());
        holder.textViewKey.setText(currentItem.getKey());
        holder.textViewValue.setText(currentItem.getValue());
        holder.textViewNumber.setText(currentItem.getRowNumberAsString());

    }

    @Override
    public int getItemCount() {
           return parsedEntries.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTableName;
        public TextView textViewKey;
        public TextView textViewValue;
        public TextView textViewNumber;

        public ItemViewHolder(@NonNull View parsedEntries) {
            super(parsedEntries);
            textViewTableName = parsedEntries.findViewById(R.id.text_view_table_name);
            textViewKey = parsedEntries.findViewById(R.id.text_view_key);
            textViewValue = parsedEntries.findViewById(R.id.text_view_value);
            textViewNumber = parsedEntries.findViewById(R.id.text_view_number);

            textViewTableName.setOnClickListener(v ->{
                listener.sendMeTableName(textViewTableName.getText().toString());
            });

        }
    }

    public interface sendMeInfoListener{
        void sendMeTableName(String name);

    }
}
