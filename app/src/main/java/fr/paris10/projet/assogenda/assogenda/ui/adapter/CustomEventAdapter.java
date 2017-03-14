package fr.paris10.projet.assogenda.assogenda.ui.adapter;

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
import fr.paris10.projet.assogenda.assogenda.model.Event;

public class CustomEventAdapter extends ArrayAdapter<Event> {

    public CustomEventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.item_event_listview, events);
    }

    private static class ViewHolder {

        private ImageView eventPicture;
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

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.eventName = (TextView) view.findViewById(R.id.item_event_listview_event_name);
        viewHolder.eventPicture = (ImageView) view.findViewById(R.id.item_event_listview_event_picture);

        viewHolder.eventName.setText(event.name);

        viewHolder.eventPicture.setImageResource(R.mipmap.ic_launcher);

        return view;
    }
}
