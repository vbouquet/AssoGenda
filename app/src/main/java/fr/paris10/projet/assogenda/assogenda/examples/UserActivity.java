package fr.paris10.projet.assogenda.assogenda.examples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.model.User;

/**
 * Activity example to show how to add/remove/update elements to database
 * and display elements in a listView with a customAdapter
 */
public class UserActivity extends AppCompatActivity {
    Button addButton;
    Button clearButton;
    ListView listView;
    private DatabaseReference database;
    private CustomUserAdapter adapter;
    public ArrayList<User> items;

    /**
     * Random list of users for example purpose
     */
    private final Random rand = new Random();
    private List<User> randomUsers = Arrays.asList(
            new User("Daniel", "Balavoine", "yeux@revolver.fr"),
            new User("Jean-Michel", "Jarre", "voir@rien.fr"),
            new User("Albert", "Dupontel", "dupont@lala.fr"),
            new User("Grumzl", "FrpMLndz", "alpha@ducentaure.fr")
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_example);

        /**
         * Init users and connection to database
         */
        items = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("users");

        /**
         * Set adapter for listview (cf customAdapter for more informations)
         */
        adapter = new CustomUserAdapter(this, items);
        listView = (ListView) findViewById(R.id.activity_user_listview);
        listView.setAdapter(adapter);

        /**
         * Listen to database users modification and update view
         * - onChildAdded, add user to listView
         * - onCancelled, log error specified by database
         */
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                user.id = dataSnapshot.getKey();
                items.add(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled", databaseError.toString());
            }
        });

        /**
         * Add user to database
         */
        addButton = (Button) findViewById(R.id.activity_user_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.push().setValue(randomUsers.get(rand.nextInt(3)));
            }
        });


        /**
         * Remove all users from database and view
         */
        clearButton = (Button) findViewById(R.id.activity_user_clear);
        clearButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                database.removeValue();
                items.clear();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        /**
         * Remove selected user from database and view
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                database.child(items.get(position).id).removeValue();
                items.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
