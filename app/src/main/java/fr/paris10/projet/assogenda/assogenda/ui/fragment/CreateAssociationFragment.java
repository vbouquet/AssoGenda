package fr.paris10.projet.assogenda.assogenda.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.paris10.projet.assogenda.assogenda.R;

/**
 * A simple {@link Fragment} subclass.
 * Create an instance of this fragment.
 */
public class CreateAssociationFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AssociationMainFragment.
     */
    public static CreateAssociationFragment newInstance() {
        return new CreateAssociationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_create_association, container, false);
        Button buttonAddImage = (Button)
                v.findViewById(R.id.fragment_create_association_button_logo);
        buttonAddImage.setOnClickListener(this);

        Button buttonValidate = (Button)
                v.findViewById(R.id.fragment_create_association_button_validate);
        buttonValidate.setOnClickListener(this);

        listenerAssociationNameInput(v);

        return v;
    }

    //For API Level 23
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (CreateAssociationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new FragmentRuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //For API Level 22 (deprecated)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CreateAssociationFragment.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new FragmentClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Manage buttons actions.
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fragment_create_association_button_validate:
                mListener.onCreateAssociationFragmentInteraction();
                break;

            case R.id.fragment_create_association_button_logo:
                mListener.onAddImageAssociationFragmentInteraction();
                break;

            default:
                break;
        }
    }

    /**
     * Notify user if association name is already taken
     */
    private void listenerAssociationNameInput(View v) {
        final EditText input = (EditText) v.findViewById(R.id.fragment_create_association_name);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("afterTextChanged", "before text changed : " + charSequence.toString());
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                FirebaseDatabase.getInstance().getReference("association")
                        .orderByChild("clear_name")
                        .equalTo(charSequence.toString().toLowerCase().trim().replaceAll(" ", ""))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    input.setError(String.format("%s is already taken", charSequence.toString()));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("onCancelled", "validateAssocationName : " + databaseError.getMessage());
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("afterTextChanged", "After text changed : " + editable.toString());
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {

        void onCreateAssociationFragmentInteraction();
        void onAddImageAssociationFragmentInteraction();
    }
}