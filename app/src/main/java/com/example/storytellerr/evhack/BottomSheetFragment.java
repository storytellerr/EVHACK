package com.example.storytellerr.evhack;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;



public class BottomSheetFragment extends BottomSheetDialogFragment {


    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        RatingBar rb = v.findViewById(R.id.ratings);
        Bundle mArgs = getArguments();
        String name = mArgs.getString("name");
        String stars = mArgs.getString("stars");
       // String phone = mArgs.getString("phone");
        String add = mArgs.getString("address");
        TextView shop_name = v.findViewById(R.id.shop_name);
        TextView phone_no = v.findViewById(R.id.phone);
        TextView address_shop = v.findViewById(R.id.address);
        shop_name.setText(name);
       // phone_no.setText(phone);
        address_shop.setText(add);
        rb.setNumStars(Integer.parseInt(stars));
       // stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        return v;
    }

}
