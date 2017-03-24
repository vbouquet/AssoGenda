package fr.paris10.projet.assogenda.assogenda.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;

/**
 * Adapter used to display associations of a user
 */
public class CustomAssociationAdapter extends ArrayAdapter<Association> {

    public CustomAssociationAdapter(Context context, ArrayList<Association> association) {
        super(context, R.layout.item_association_listview, association);
    }

    private static class ViewHolder {
        private TextView textAssociationName;
        private ImageView imageAssociationLogo;
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

        //set association name
        viewHolder.textAssociationName.setText(association.name);

        //Set association logo
        if (association.logo != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            StorageReference imagePath = storageReference.child(association.logo);

            Glide.with(this.getContext())
                    .using(new FirebaseImageLoader())
                    .load(imagePath)
                    .into(viewHolder.imageAssociationLogo);
        }

        return view;
    }
}
