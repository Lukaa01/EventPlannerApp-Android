package com.example.eventplannerapp.fragments.organizator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.EventType;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.databinding.FragmentBudgetPlanBinding;
import com.example.eventplannerapp.fragments.vlasnik.ProductListFragment;
import com.example.eventplannerapp.fragments.vlasnik.ProductsPageViewModel;
import com.example.eventplannerapp.fragments.vlasnik.ServiceListFragment;
import com.example.eventplannerapp.fragments.vlasnik.ServicePageViewModel;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetPlanFragment extends Fragment implements ProductListFragment.OnProductSelectedListener  {

    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Service> services = new ArrayList<>();
    private ArrayList<Product> selectedProducts = new ArrayList<>();
    private ArrayList<Service> selectedServices = new ArrayList<>();
    private ServicePageViewModel servicePageViewModel;
    private ProductsPageViewModel productsPageViewModel;
    private FragmentBudgetPlanBinding binding;
    private NavController navController;
    /*
    - products
    - viewmodel/adapter
    - binding
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetPlanFragment newInstance(String param1, String param2) {
        BudgetPlanFragment fragment = new BudgetPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productsPageViewModel = new ViewModelProvider(this).get(ProductsPageViewModel.class);
        servicePageViewModel = new ViewModelProvider(this).get(ServicePageViewModel.class);
        binding = FragmentBudgetPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        products = new ArrayList<>();
        prepareServiceList(services);
        prepareProductList(products);
        binding.buttonProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FragmentTransition.to(ProductListFragment.newInstance(products), getActivity(),
                        false, R.id.downView);*/
                products = new ArrayList<>();
                prepareProductList(products);
            }
        });

        binding.buttonServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(ServiceListFragment.newInstance(services), getActivity(),
                        false, R.id.downView);
            }
        });

        binding.createItemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main_org);
                navController.navigate(R.id.nav_planned_items);
            }
        });

        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void prepareProductList(ArrayList<Product> products){

        CloudStoreUtil.selectAllProducts().thenAccept(productList -> {
            // Ovdje obradite listu proizvoda
            products.addAll(productList);
            // .to metoda je premestena ovde zbog asinhronog izvrsavanja
            FragmentTransition.to(ProductListFragment.newInstance(products), getActivity(),
                    false, R.id.downView);

            //productListAdapter = new ProductListAdapter(getContext(), products);

        }).exceptionally(exception -> {
            // Ovdje rukujte iznimkom
            return null; // ili povratna vrijednost u slučaju iznimke
        });

        /*
        ArrayList<EventType> list = new ArrayList<>();
        list.add(EventType.BAPTISMS);
        list.add(EventType.WEDDINGS);

        String[] slike = new String[]{"slika1.jpg", "slika2.jpg", "slika3.jpg"};

        products.add(new Product(1L, "Photo and video", "Description 1", "Photo and video", "Albumi", 2000, 4,1850, slike, "privatni dogadjaj", true, true));
        products.add(new Product(2L, "Photo and video", "Description 1", "Stampanje majica", "Description 2", 3200, 10,3000, slike, "privatni dogadjaj", true, true));
        products.add(new Product(3L, "Photo and video", "Description 1", "Snimanje dronom", "Description 3", 5200, 7,4500, slike, "privatni dogadjaj", true, true));
        products.add(new Product(4L, "Photo and video", "Description 1", "Snimanje privatnih dogadjaja", "Description 4", 12000, 7,10000, slike, "privatni dogadjaj", true, true));
  */ }
    private void prepareServiceList(ArrayList<Service> services){
        ArrayList<EventType> list = new ArrayList<>();
        list.add(EventType.BAPTISMS);
        list.add(EventType.WEDDINGS);

        ArrayList<String> slike = new ArrayList<>();
        slike.add("Slika1");
        slike.add("Slika2");

        ArrayList<String> imena = new ArrayList<>();
        imena.add("Petar Petrovic");
        imena.add("Igor Petrovic");

        //services.add(new Service(1L, "Matursko slikanje", "Description 1", Category.PHOTO_VIDEO, Subcategory.ALBUMS, 2000, 4, 2, "NS", imena,"nista spec",list,slike, "12 meseci","2 dana",true, true, true));
        //services.add(new Service(2L, "Snimanje dronom", "Description 2", Category.PHOTO_VIDEO, Subcategory.DRONE_RECORDING, 6000, 4, 2, "NS", imena,"nista spec",list,slike, "12 meseci","2 dana",true, true, true));
        //services.add(new Service(3L, "Snimanje svadbe", "Description 3", Category.PHOTO_VIDEO, Subcategory.VIDEO_MATERIALS, 4000, 4, 2, "NS", imena,"nista spec",list,slike, "12 meseci","2 dana",true, true, true));
        //services.add(new Service(4L, "Nemam ideju", "Description 4", Category.PHOTO_VIDEO, Subcategory.ALBUMS, 2500, 4, 2, "NS", imena,"nista spec",list,slike, "12 meseci","2 dana",true, true, true));
    }
    @Override
    public void onProductSelected(Product product) {
        // Handle the selected product here
        // For demonstration, let's just add it to the selected products list
        selectedProducts.add(product);
        // Update UI or perform any necessary actions based on the selected product
        // For example, you can display the selected products somewhere in your UI
    }
}