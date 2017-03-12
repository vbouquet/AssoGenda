package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import fr.paris10.projet.assogenda.assogenda.R;
import fr.paris10.projet.assogenda.assogenda.daos.DAOUser;
import fr.paris10.projet.assogenda.assogenda.model.Association;
import fr.paris10.projet.assogenda.assogenda.ui.adapter.SearchAssociationAdapter;

/**
 * - TODO onItemClick follow association
 * - TODO add button display following association
 */
public class ListAssociationActivity extends AppCompatActivity {

    public ArrayList<Association> associations;
    private SearchAssociationAdapter adapter;
    private ListView listView;
    private DatabaseReference database;
    private SearchView searchView;
    private DAOUser userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_association);

        database = FirebaseDatabase.getInstance().getReference("association");
        userDatabase = DAOUser.getInstance();

        associations = new ArrayList<>();

        database.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Association association = dataSnapshot.getValue(Association.class);
                association.id = dataSnapshot.getKey();
                associations.add(association);
                adapter.add(association);
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

            }
        });

        adapter = new SearchAssociationAdapter(this, associations);
        listView = (ListView) findViewById(R.id.list_association_activity_list_view);
        listView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.list_association_activity_search);

        /**
         * Display association corresponding to user input
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        /**
        * Display back all association
         */
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.filter("");
                searchView.clearFocus();
                return true;
            }
        });

        /**
         * Confirm follow association
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Association asso = adapter.getItem(position);

                userDatabase.followOrUnfollowAssociation(asso);
                //TODO If user already follow association then unfollow
                //TODO else follow association
            }
        });
    }

//    public Dialog confirmFollowAssociation(Association association) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Voulez vous suivre " + )
//                .setPositiveButton(R.string.fragment_create_association_form_validation_ok,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //TODO FOLLOW ASSOCIATION
//                            }
//                        })
//                .setNegativeButton(R.string.fragment_create_association_form_validation_cancel,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                            }
//                        });
//        return builder.create();
//    }
}
