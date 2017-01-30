package fr.paris10.projet.assogenda.assogenda.ui.activites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fr.paris10.projet.assogenda.assogenda.R;

/**
 * A simple {@link Fragment} subclass.
 * Create an instance of this fragment.
 */
public class CreateAssociationFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public CreateAssociationFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AssociationMainFragment.
     */
    public static CreateAssociationFragment newInstance() {
        CreateAssociationFragment fragment = new CreateAssociationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_create_association, container, false);
        Button buttonValidate = (Button)
                v.findViewById(R.id.fragment_create_association_button_validate);
        buttonValidate.setOnClickListener(this);
        return v;
    }

    //For API Level 23
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onAttach Context");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (CreateAssociationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //For API Level 22 (deprecated)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(this.getClass().getCanonicalName(), "Entre dans onAttach Activity");
        try {
            mListener = (CreateAssociationFragment.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(this.getClass().getCanonicalName(), "Entre dans onDetach");
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Log.i(this.getClass().getCanonicalName(), "Entre dans onClick ");
        mListener.onCreateAssociationFragmentInteraction();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {

        void onCreateAssociationFragmentInteraction();
    }
}