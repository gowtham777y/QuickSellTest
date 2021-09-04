package com.qstest.FireBaseData;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qstest.Model.listItem;

import java.util.ArrayList;
import java.util.List;

public class Repo {

    static Repo instance;
    private static List<String> productIds;
    private List<listItem> productItems = new ArrayList<>();
//    private List<String> productIds = new ArrayList<>();
    MutableLiveData<List<listItem>> products = new MutableLiveData<>();


    public static Repo getInstance(List<String> productId){
        if (instance == null){
            instance = new Repo();
        }
        productIds=productId;
        return instance;
    }

    public MutableLiveData<List<listItem>> getProducts(){
        if (productItems.size() == 0){
            loadProducts();
        }

        products.setValue(productItems);
        return products;
    }

    private void loadProducts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i=0;i<productIds.size();i++){
                    int finalI = i;
                    String productName = snapshot.child("product-name/"+productIds.get(finalI)).getValue(String.class);
                    String productDesc = snapshot.child("product-desc/"+productIds.get(finalI)).getValue(String.class);
                    Long productPrice = snapshot.child("product-price/"+productIds.get(finalI)).getValue(Long.class);
                    String productImage = snapshot.child("product-image/"+productIds.get(finalI)).getValue(String.class);


                    if (productName == null) {
                        productName = "";
                    }
                    if (productDesc == null) {
                        productDesc = "";
                    }
                    if (productImage == null) {
                        productImage = "";
                    }
                    if (productPrice == null) {
                        productPrice = 0L;
                    }
                    productItems.add(new listItem(productName, productDesc, productPrice, productImage));
                }
                products.postValue(productItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
