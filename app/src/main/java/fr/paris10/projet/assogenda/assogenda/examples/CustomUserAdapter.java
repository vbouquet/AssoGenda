package fr.paris10.projet.assogenda.assogenda.examples;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.User;

class CustomUserAdapter extends ArrayAdapter<User> {
    CustomUserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    private static class ViewHolder {
        private TextView textFirstName;
        private TextView textLastName;
        private TextView textEmail;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        View view = convertView;

        if (convertView == null) {
             view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_user_example, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.textFirstName = (TextView) view.findViewById(R.id.item_user_example_firstname);
        viewHolder.textLastName = (TextView) view.findViewById(R.id.item_user_example_lastname);
        viewHolder.textEmail = (TextView) view.findViewById(R.id.item_user_example_email);

        viewHolder.textFirstName.setText(user.firstName);
        viewHolder.textLastName.setText(user.lastName);
        viewHolder.textEmail.setText(user.email);

        return view;
    }
}
