package com.qstest.APIRequests;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class listItemViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> liveData=new MutableLiveData<>();
    private String url;

    public listItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUrl(String urlString){
        this.url=urlString;
    }

    public void doAction(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    if (url.length() == 0){
                        return;
                    }
                    liveData.postValue(QueryUtils.fetchData(url));
                }
            }
        };

        Thread thread=new Thread(runnable);
        thread.start();
    }

    public MutableLiveData<List<String>> getData(){
        return liveData;
    }

}
