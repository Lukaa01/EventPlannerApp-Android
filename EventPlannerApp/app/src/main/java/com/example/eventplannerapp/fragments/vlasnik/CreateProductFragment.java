package com.example.eventplannerapp.fragments.vlasnik;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentCreateProductBinding;
import com.example.eventplannerapp.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCreateProductBinding binding;

    private List<String> imageUrlList;
    private EditText urlEditText;
    private ListView imageListView;
    private ArrayAdapter<String> adapter;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    public static CreateProductFragment newInstance(String param) {
        CreateProductFragment fragment = new CreateProductFragment();
        Bundle args = new Bundle();
        args.putString(param, "PARAM");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] optionsCategory = getResources().getStringArray(R.array.categories);
        String[] optionsSubcategory = getResources().getStringArray(R.array.subcategories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, optionsCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterSubcategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, optionsSubcategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = binding.spinnerCategory;
        spinner.setAdapter(adapter);
        Spinner spinnerSubcategory = binding.spinnerSubcategory;
        spinnerSubcategory.setAdapter(adapterSubcategory);

        imageUrlList = new ArrayList<>();
        ArrayAdapter<String> adapterImages = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, imageUrlList);

        imageListView = binding.imageURLListView;
        imageListView.setAdapter(adapterImages);

        urlEditText = binding.urlEditText;
        Button addURLButton = binding.addURLButton;
        addURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = urlEditText.getText().toString();
                if (!imageUrl.isEmpty()) {
                    imageUrlList.add(imageUrl);
                    adapterImages.notifyDataSetChanged();
                    urlEditText.getText().clear();
                }
            }
        });

        Button addButton = binding.addButton;
        addButton.setOnClickListener(v -> {
            String name = binding.nameTextView.getText().toString();
            String description = binding.descriptionTextView.getText().toString();
            String typeEvent = binding.typeEventTextView.getText().toString();
            String price = binding.priceEditText.getText().toString();
            String discount = binding.discountEditText.getText().toString();
            String category = binding.spinnerCategory.getSelectedItem().toString();
            String subcategory = binding.spinnerSubcategory.getSelectedItem().toString();
            Integer priceInteger = parseInt(price);
            Integer discountInteger = parseInt(discount);
            Boolean available = binding.availableCheckBox.isChecked();
            Boolean visible = binding.visibleCheckBox.isChecked();
            List<String> imagesList = new ArrayList<>();
            if (adapterImages != null) {
                int count = adapterImages.getCount();
                for (int i = 0; i < count; i++) {
                    imagesList.add(adapterImages.getItem(i));
                }
            }
            if(!name.isEmpty() || !description.isEmpty() || !typeEvent.isEmpty() || priceInteger > 0 || discountInteger > 0 && discountInteger < 100){
                Product product = new Product(category, subcategory, name, description, priceInteger, discountInteger, (priceInteger - discountInteger*priceInteger/100), imagesList, typeEvent, available, visible, false);
                CloudStoreUtil.insertProduct(product);
                Toast.makeText(getContext(), "Product succesfully added!", Toast.LENGTH_SHORT).show();
                binding.nameTextView.setText("");
                binding.descriptionTextView.setText("");
                binding.typeEventTextView.setText("");
                binding.priceEditText.setText("");
                binding.discountEditText.setText("");
                binding.visibleCheckBox.setChecked(false);
                binding.availableCheckBox.setChecked(false);
                binding.urlEditText.setText("");
                binding.spinnerCategory.setSelection(0);
                binding.spinnerSubcategory.setSelection(0);
                adapterImages.clear();
                adapterImages.notifyDataSetChanged();
            }
        });

        return root;
    }
}