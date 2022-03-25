package com.example.uscdrinkdoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context mContext;
    private int mResource;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView name = convertView.findViewById(R.id.userName);
        TextView description = convertView.findViewById(R.id.Description);
        TextView price = convertView.findViewById(R.id.Price);
        TextView caffeine = convertView.findViewById(R.id.userCaffeine);

        name.setText(getItem(position).getName());
        description.setText(getItem(position).getDescription());

        String priceString = "$" + String.valueOf(getItem(position).getPrice());
        price.setText(priceString);

        String caffeineString = String.valueOf(getItem(position).getCaffeine()) + " mg";
        caffeine.setText(caffeineString);





        return convertView;
    }
}
