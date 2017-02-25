package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

public class CustomAssociationAdapter extends ArrayAdapter<Association>{

    public CustomAssociationAdapter(Context context, ArrayList<Association> association) {
        super(context, 0, association);
    }

    private static class ViewHolder {
        TextView textAssociationName;
        ImageView imageAssociationLogo;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Association association = getItem(position);
        View view = convertView;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_association_listview, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.textAssociationName = (TextView) view.findViewById(R.id.item_association_listview_association_name);
        viewHolder.imageAssociationLogo = (ImageView) view.findViewById(R.id.item_association_listview_association_logo);

        viewHolder.textAssociationName.setText(association.name);

        //TODO Set association logo instead of default image
        //TODO Add a custom default image
        viewHolder.imageAssociationLogo.setImageResource(R.mipmap.ic_launcher);

        return view;
    }
}
