package com.example.calorimety.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calorimety.R;

import java.util.List;


public class GroupNameSpinnerAdapter extends ArrayAdapter<String> {
    List<String> list;
    public GroupNameSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        list = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_item, null);
        }
        ((TextView) convertView.findViewById(R.id.tv_spinner_item))
                .setText(list.get(position));
        return convertView;
    }
}
