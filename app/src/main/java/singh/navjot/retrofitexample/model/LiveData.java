package singh.navjot.retrofitexample.model;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveData extends ViewModel {
    MutableLiveData<List<UserDetail>> mutableLiveData = new MutableLiveData<>();

    public void setMutableLiveData(List<UserDetail> userdail) {
        mutableLiveData.postValue(userdail);
    }

    public MutableLiveData<List<UserDetail>> getMutableLiveData() {
        return mutableLiveData;
    }

}
