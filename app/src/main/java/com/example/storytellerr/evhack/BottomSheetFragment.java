package com.example.storytellerr.evhack;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {


    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
       TextView tv=v.findViewById(R.id.name);
        Bundle mArgs = getArguments();
        String name = mArgs.getString("name");
        String distance = mArgs.getString("distance");
        tv.setText(name + distance);
        return v;
    }

}
