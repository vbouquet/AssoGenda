package fr.paris10.projet.assogenda.assogenda.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;
import fr.paris10.projet.assogenda.assogenda.model.Association;

/**
 * Adapter used to display associations and enable user to follow them
 */
public class SearchAssociationAdapter extends ArrayAdapter<Association> {
    final private ArrayList<Association> associationsDisplayed;
    final private ArrayList<Association> associationsValues;
    private DAOUser userDatabase;

    public SearchAssociationAdapter(Context context, ArrayList<Association> associations) {
        super(context, 0, associations);
        this.associationsDisplayed = new ArrayList<>();
        this.associationsDisplayed.addAll(associations);
        this.associationsValues = associations;
        this.userDatabase = DAOUser.getInstance();
    }

    private static class ViewHolder {
        private TextView name;
        private TextView university;
        private ImageView logo;
        private ImageButton followButton;
    }

    @Override
    public void add(@Nullable Association object) {
        associationsDisplayed.add(object);
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

        final Association association = this.getItem(position);
        View view = convertView;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_association_list_association, parent, false);
        }

        final ViewHolder viewHolder = new ViewHolder();

        viewHolder.followButton = (ImageButton) view.findViewById(R.id.item_association_list_association_follow);
        if (association.followed) {
            viewHolder.followButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            viewHolder.followButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
        viewHolder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followOrUnfollowAssociation(association);
                notifyDataSetChanged();
            }
        });
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
            viewHolder.logo.setImageResource(R.drawable.association_default_icon);
        }

        return view;
    }

    /**
     * Filter association depending on user input :
     * - search by association name
     * - search by univerty
     */
    public void filter(String search) {
        search = search.toLowerCase(Locale.getDefault());

        this.associationsDisplayed.clear();

        if (search.length() == 0) {
            associationsDisplayed.addAll(associationsValues);
        } else {
            for (Association association : associationsValues) {
                if (association.name.toLowerCase(Locale.getDefault()).contains(search)) {
                    this.associationsDisplayed.add(association);
                } else if (association.university.toLowerCase(Locale.getDefault()).contains(search)) {
                    this.associationsDisplayed.add(association);
                }
            }
        }
        notifyDataSetChanged();
    }


    /**
     * Follow or unfollow given association
     */
    private void followOrUnfollowAssociation(final Association association) {
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("user-follow-asso");
        final String uid = userDatabase.getCurrentUserId();

        db.child(uid).child(association.id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // check if user is following or not
                if (dataSnapshot.exists()) {
                    db.child(uid).child(association.id).removeValue();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put(association.id, association.name);
                    db.child(uid).updateChildren(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled", databaseError.getMessage());
            }
        });
        association.followed = !association.followed;
    }
}
