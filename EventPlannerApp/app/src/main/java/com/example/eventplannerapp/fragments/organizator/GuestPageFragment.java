package com.example.eventplannerapp.fragments.organizator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.GuestListAdapter;
import com.example.eventplannerapp.databinding.FragmentEventPageBinding;
import com.example.eventplannerapp.databinding.FragmentGuestPageBinding;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.EventActivity;
import com.example.eventplannerapp.model.Guest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestPageFragment extends Fragment {
    public static ArrayList<Guest> guests = new ArrayList<>();
    private GuestPageViewModel guestPageViewModel;
    private FragmentGuestPageBinding binding;
    private NavController navController;



    public GuestPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestPageFragment newInstance() {
        return new GuestPageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        guestPageViewModel = new ViewModelProvider(this).get(GuestPageViewModel.class);
        binding = FragmentGuestPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        guests = new ArrayList<>();
        guests = prepareGuestList(guests);

        Button btn = binding.btnGeneratePdf;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf(guests);
            }
        });


        // Inflate the layout for this fragment
        return root;
    }
    private ArrayList<Guest> prepareGuestList(ArrayList<Guest> guests) {
        CloudStoreUtil.selectAllGuests().thenAccept(list -> {
            // Ovdje obradite listu proizvoda
            guests.addAll(list);
            // Log all events
            for (Guest guest : list) {
                Log.d("GuestPageFragment", "Guest: " + guest.toString());
            }

            // .to metoda je premestena ovde zbog asinhronog izvrsavanja
            getActivity().runOnUiThread(() -> {
                FragmentTransition.to(GuestListFragment.newInstance(guests), getActivity(),
                        false, R.id.scroll_guest_list);
            });


            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });
        return guests;
    }
    private void generatePdf(ArrayList<Guest> guests) {

        Log.d("EventActivity", "Activity size in generate pdf: " + guests.size());

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(612, 792, 1).create(); // Standard US Letter size: 8.5 x 11 inches

        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Paint object for the title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(32); // Larger text size for the title
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Draw the title
        canvas.drawText("Guests List", 50, 50, titlePaint);

        // Paint object for the event details
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(18); // Smaller text size for the event details

        int y = 100; // Y coordinate for the start of the event details, after the title

        // Print details of each event on the PDF page
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            canvas.drawText("Guest Name: " + guest.getGuestName(), 50, y, paint);
            y += 25;
            canvas.drawText("Age group: " + guest.getAgeGroup(), 50, y, paint);
            y += 25;
            canvas.drawText("Is he invited?: " + guest.isInvited(), 50, y, paint);
            y += 25;
            canvas.drawText("Is he accepted?: " + guest.isAccepted(), 50, y, paint);
            y += 25;
            canvas.drawText("Special request: " + guest.getSpecialRequests(), 50, y, paint);
            y += 40; // Space between each event
        }

        // Finish the page
        document.finishPage(page);

        // Define the file path to save the PDF
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "mobOdbranaGuestList.pdf");

        try {
            // Save the PDF to the specified location
            document.writeTo(new FileOutputStream(file));
            // Notify the user that the PDF was generated successfully
            Toast.makeText(getContext(), "PDF generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle IOException if any occurs
            e.printStackTrace();
            // Notify the user about the failure to generate the PDF
            Toast.makeText(getContext(), "Failed to generate PDF!", Toast.LENGTH_SHORT).show();
        } finally {
            // Close the document to release resources
            document.close();
        }
    }
}