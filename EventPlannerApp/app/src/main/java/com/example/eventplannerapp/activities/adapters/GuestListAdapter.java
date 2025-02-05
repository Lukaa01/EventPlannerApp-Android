package com.example.eventplannerapp.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.fragments.organizator.GuestListFragment;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.Guest;

import java.util.ArrayList;

public class GuestListAdapter extends ArrayAdapter<Guest> {
    private ArrayList<Guest> aGuests;
    private NavController navController;

    public GuestListAdapter(Context context, ArrayList<Guest> guests) {
        super(context, R.layout.guest_card, guests);
        aGuests = guests;
    }
    @Override
    public int getCount() {
        return aGuests.size();
    }
    @Nullable
    @Override
    public Guest getItem(int position) {
        return aGuests.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Guest guestItem = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_card,
                    parent, false);
        }
        LinearLayout guestCard = convertView.findViewById(R.id.guest_card);
        TextView guestName = convertView.findViewById(R.id.guest_name);
        TextView ageGroup = convertView.findViewById(R.id.age_group);
        TextView invited = convertView.findViewById(R.id.invited);
        TextView accepted = convertView.findViewById(R.id.accepted);
        TextView specialRequests = convertView.findViewById(R.id.special_requests);

        Button btn = convertView.findViewById(R.id.delete_guest_button);
        Button navBtn = convertView.findViewById(R.id.change_guest_button);



        if(guestItem != null) {
            guestName.setText(guestItem.getGuestName());
            ageGroup.setText(guestItem.getAgeGroup());
            invited.setText(guestItem.isInvited() ? "Yes" : "No");
            accepted.setText(guestItem.isAccepted() ? "Yes" : "No");
            specialRequests.setText(guestItem.getSpecialRequests());

            guestCard.setOnClickListener(v -> {
                Log.i("ShopApp", "Clicked: " + guestItem.getGuestName() + ", id: " +
                        guestItem.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + guestItem.getGuestName() +
                        ", id: " + guestItem.getId().toString(), Toast.LENGTH_SHORT).show();
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Guest> guests = new ArrayList<>();
                    CloudStoreUtil.deleteGuest(guestItem.getId());
                    CloudStoreUtil.selectAllGuests().thenAccept(list -> {
                        guests.addAll(list);
                        if (getContext() instanceof FragmentActivity) {
                            FragmentActivity activity = (FragmentActivity) getContext();
                            activity.runOnUiThread(() -> {
                                FragmentTransition.to(GuestListFragment.newInstance(guests), activity,
                                        false, R.id.scroll_guest_list);
                            });
                        }

                    }).exceptionally(exception -> {
                        // Ovdje rukujte iznimkom
                        return null; // ili povratna vrijednost u slučaju iznimke
                    });
                }
            });

            navBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController = Navigation.findNavController((Activity) getContext(), R.id.fragment_nav_content_main_org);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("guest", guestItem);
                    navController.navigate(R.id.nav_guest_update, bundle);
                }
            });


        }
        return convertView;

    }


}






