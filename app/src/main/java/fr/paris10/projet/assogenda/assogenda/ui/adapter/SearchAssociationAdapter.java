package fr.paris10.projet.assogenda.assogenda.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.ui.activites.ListAssociationActivity;

/**
 * Adapter used to display associations of a user
 */
public class SearchAssociationAdapter extends ArrayAdapter<Association> {
    private ArrayList<Association> associationsDisplayed;
    private ArrayList<Association> associationsValues;

    public SearchAssociationAdapter(Context context, ArrayList<Association> associations) {
        super(context, 0, associations);
        this.associationsDisplayed = new ArrayList<>();
        this.associationsDisplayed.addAll(associations);
        this.associationsValues = associations;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView university;
        private ImageView logo;
    }

    @Override
    public int getCount() {
        return associationsDisplayed.size();
    }

    @Nullable
    @Override
    public Association getItem(int position) {
        return associationsDisplayed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Association association = this.getItem(position);
        View view = convertView;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_association_list_association, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.name = (TextView) view.findViewById(R.id.item_association_list_association_name);
        viewHolder.university = (TextView) view.findViewById(R.id.item_association_list_association_university);
        viewHolder.logo = (ImageView) view.findViewById(R.id.item_association_list_association_logo);

        viewHolder.name.setText(association.name);
        viewHolder.university.setText(association.university);

        if (association.logo != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            StorageReference imagePath = storageReference.child(association.logo);

            Glide.with(this.getContext())
                    .using(new FirebaseImageLoader())
                    .load(imagePath)
                    .into(viewHolder.logo);
        } else {

            //If association has no logo registered, set default android image
            viewHolder.logo.setImageResource(R.mipmap.ic_launcher);
        }

        Log.d("GetView", association.toString());
        return view;
    }

    /**
     * Filter association depending on user input :
     * - search by association name
     * - search by univerty
     *
     * @param text
     */
    public void filter(String text) {
        Log.d("Filter", "Message : " + text);
        text = text.toLowerCase(Locale.getDefault());

        this.associationsDisplayed.clear();

        if (text.length() == 0) {
            associationsDisplayed.addAll(associationsValues);
        } else {
            for (Association association : associationsValues) {
                if (association.name.toLowerCase(Locale.getDefault()).contains(text)) {
                    this.associationsDisplayed.add(association);
                } else if (association.university.toLowerCase(Locale.getDefault()).contains(text)) {
                    this.associationsDisplayed.add(association);
                }
            }
        }
        notifyDataSetChanged();
    }
}
