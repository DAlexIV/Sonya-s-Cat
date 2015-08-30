package com.example.alexiv.sonyascat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class InputFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String defTextUp = "Type smth funny here";
    private static final String defTextDown = "And here too";
    private static EditText upText;
    private static EditText downText;
    private static Button mButton;

    private OnFragmentListener mListener;
    private Activity mActivity;


    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_input, container, false);
        upText = (EditText) mView.findViewById(R.id.editTextUp);
        downText = (EditText) mView.findViewById(R.id.editTextDown);
        mButton = (Button) mView.findViewById(R.id.myButton);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onFragmentInteraction(upText.getText().toString(),
                                downText.getText().toString());
                    }
                }
        );

        return mView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentListener) activity;
            mActivity = activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String up, String down);
    }

}
