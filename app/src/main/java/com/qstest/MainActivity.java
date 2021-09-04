package com.qstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qstest.APIRequests.listItemViewModel;
import com.qstest.Model.listItem;
import com.qstest.ViewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG=MainActivity.class.getSimpleName();

    private RecyclerView listRecyclerView;
    private static final String API_REQUEST_URI="http://35.154.26.203/product-ids";
    private ProgressBar progressBar;
    private ProductViewModel productViewModel;
    private listItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listRecyclerView=findViewById(R.id.items_recyclerView);
        progressBar=findViewById(R.id.progress_circular);

        listItemViewModel listItemViewModel = new ViewModelProvider(this).get(com.qstest.APIRequests.listItemViewModel.class);
        listItemViewModel.setUrl(API_REQUEST_URI);
        listItemViewModel.doAction();

        listItemViewModel.getData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                displayData(strings);
            }
        });

    }

    private void displayData(List<String> strings) {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.init(strings);

        adapter = new listItemAdapter(productViewModel.getProducts().getValue(),this);
        productViewModel.getProducts().observe(this, new Observer<List<listItem>>() {
            @Override
            public void onChanged(List<listItem> listItems) {
                adapter.notifyDataSetChanged();
            }
        });
        listRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}