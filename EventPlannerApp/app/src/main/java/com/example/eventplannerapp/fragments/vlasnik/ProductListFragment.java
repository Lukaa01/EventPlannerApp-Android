package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.adapters.ProductListAdapter;
import com.example.eventplannerapp.databinding.FragmentProductsPageBinding;
import com.example.eventplannerapp.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends ListFragment {

    private ProductListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Product> mProducts;

    // Interface to communicate item click back to the parent fragment
    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }
    private OnProductSelectedListener mListener;

    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductListFragment newInstance(ArrayList<Product> products) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ProductListAdapter(getActivity(), mProducts);
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Get the clicked product
        Product selectedProduct = (Product) l.getItemAtPosition(position);

        System.out.println("Selected Product: " + selectedProduct.getName());
        // Pass the selected product back to the parent fragment
        mListener.onProductSelected(selectedProduct);
    }
}