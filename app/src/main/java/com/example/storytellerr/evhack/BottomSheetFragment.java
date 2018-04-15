package com.example.storytellerr.evhack;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    String []names={"Charger 1", "Charger 2","Charger 3" ,"Charger 4"};
    String []status={"true","true","true","false",};
    ListView lv;

    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
       lv=(ListView)v.findViewById(R.id.idListView);
       MyAdapter adapter=new MyAdapter(getActivity(),names,status);
       lv.setAdapter(adapter);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Get Current Date

               // Get Current Time
               final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
               int mMinute = c.get(Calendar.MINUTE);

               // Launch Time Picker Dialog
               TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                       new TimePickerDialog.OnTimeSetListener()
                       {

                           @Override
                           public void onTimeSet(TimePicker view, int hourOfDay,
                                                 int minute)
                           {

                               String time=hourOfDay+":"+minute;
                               time=time.toString();
                               //txtTime.setText(hourOfDay + ":" + minute);
                           }
                       },
                       mHour, mMinute, false);
               timePickerDialog.show();

           }
       });

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

    class MyAdapter extends ArrayAdapter{

        String [] names;
        String [] status;
        int image;

        public MyAdapter(Context context, String[] names, String [] status){
            super(context,R.layout.custom_row,R.id.textlist,names);
            this.names=names;
            this.image=R.drawable.ic_action_green;
            this.status=status;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflate=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflate.inflate(R.layout.custom_row,parent,false);

            ImageView myimage=(ImageView)row.findViewById(R.id.image);
            TextView myTitle=(TextView)row.findViewById(R.id.textlist);

            if (status[position]=="true") {
                myimage.setImageResource(image);
            }
            else{
                myimage.setImageResource(R.drawable.ic_action_red);
            }
            myTitle.setText(names[position]);
            return row;
        }
    }
}
