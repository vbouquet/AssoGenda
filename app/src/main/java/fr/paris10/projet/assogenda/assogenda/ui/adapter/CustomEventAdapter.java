package fr.paris10.projet.assogenda.assogenda.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class CustomEventAdapter extends ArrayAdapter<Event> {

    public CustomEventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.item_event_listview, events);
    }

    private static class ViewHolder {
        private ImageView assoPicture;
        private TextView eventName;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);
        View view = convertView;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_event_listview, parent, false);
        }

        final View view2 = view;

        final ViewHolder viewHolder = new ViewHolder();

        viewHolder.eventName = (TextView) view.findViewById(R.id.item_event_listview_event_name);
        viewHolder.assoPicture = (ImageView) view.findViewById(R.id.item_event_listview_event_picture);

        viewHolder.eventName.setText(event.name);

        FirebaseDatabase.getInstance().getReference("association")
                .child(event.association)
                .child("logo")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String logo = dataSnapshot.getValue(String.class);
                            if (logo != null) {
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                                StorageReference imagePath = storageReference.child(logo);

                                Glide.with(view2.getContext())
                                        .using(new FirebaseImageLoader())
                                        .load(imagePath)
                                        .into(viewHolder.assoPicture);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("onCancelled", databaseError.getMessage());
                    }
                });

        return view;
    }
}
