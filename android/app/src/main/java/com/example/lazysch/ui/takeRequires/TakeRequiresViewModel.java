package com.example.lazysch.ui.takeRequires;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TakeRequiresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TakeRequiresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is personal fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}