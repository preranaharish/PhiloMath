package com.rahulbuilds.philomath;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserViewHolder>{
    List<UserDetails> userDetailsList;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    private String senderFirstLetter;
    private TextView senderProfilePic;
    String ex;
    RecyclerViewItemListener callback;
    public UserDetailsAdapter(List<UserDetails> userDetailsList,RecyclerViewItemListener callback) {
        this.userDetailsList = userDetailsList;
        this.callback = callback;
    }
    private List<UserDetails> filter(String text) {
        //new array list that will hold the filtered data
        List<UserDetails> filterdNames = new ArrayList<UserDetails>();

        //looping through existing elements
        for (UserDetails s: this.userDetailsList) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        return filterdNames;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.card_view_of_words, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(iteView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {

        UserDetails userDetails = userDetailsList.get(position);
        holder.tvName.setText(userDetails.getName());
        if(userDetails.getProfessiion() == null){
            ex = "Not Available";
        }
        else if(userDetails.getProfessiion().isEmpty()){
             ex = "Not Available";
        }
        else{
            ex = userDetails.getProfessiion();
        }

// To retrieve first letter of the sender,

        holder.tvAddress.setText(userDetails.getAddress());
        holder.tvPhone.setText(userDetails.getMobileNo());
        holder.tvProfession.setText("Eg: "+ex+"\n");
        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserDetails userDetails = userDetailsList.get(position);
                final int userId = userDetails.getUserId();

//                db = dbHelper.getWritableDatabase();
                PopupMenu menu = new PopupMenu(context, holder.ivMenu);

                menu.inflate(R.menu.card_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Delete:
                                String word = userDetails.getName();
                                Intent intent = new Intent("message_subject_intent");
                                intent.putExtra("name" , String.valueOf(word));
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                notifyDataSetChanged();
                                notifyItemChanged(position);
                                break;
                        }
                        return false;

                    }
                });
                menu.show();
            }
        });
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView letter, tvName, tvAddress, tvPhone, tvProfession;
        ImageView ivMenu;
        public UserViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvProfession = (TextView) itemView.findViewById(R.id.tv_profession);
            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
        }
        @Override
        public void onClick(View v) {
            callback.onItemClicked(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            callback.onItemLongClicked(getLayoutPosition());
            return true;
        }
    }


}
