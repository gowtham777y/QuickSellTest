package com.qstest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qstest.Model.listItem;
import com.qstest.FireBaseData.Repo;

import java.util.List;

public class ProductViewModel extends ViewModel {

    MutableLiveData<List<listItem>> products;

    public void init(List<String> productIds){
        if (products!=null){
            return;
        }
        products = Repo.getInstance(productIds).getProducts();
    }

    public LiveData<List<listItem>> getProducts(){

        return products;
    }

}
