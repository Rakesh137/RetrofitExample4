package singh.navjot.retrofitexample;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singh.navjot.retrofitexample.adapter.MyRecyclerViewAdapter;
import singh.navjot.retrofitexample.databinding.ActivityMainBinding;
import singh.navjot.retrofitexample.db.DataBaseClient;
import singh.navjot.retrofitexample.db.Database;
import singh.navjot.retrofitexample.model.Address;
import singh.navjot.retrofitexample.model.LiveData;
import singh.navjot.retrofitexample.model.UserDetail;

public class MainActivity extends AppCompatActivity {
    LiveData liveData;
    //RecyclerView listView;
    Database cliet;
    ArrayList<Address> addresses = new ArrayList<>();
    ActivityMainBinding  binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
       // binding= ActivityMainBinding.inflate(getLayoutInflater());
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        cliet = DataBaseClient.getInstance(getApplicationContext()).database.baseDabase();
        liveData = ViewModelProviders.of(this).get(LiveData.class);
       // listView = findViewById(R.id.listViewHeroes);
        binding.listViewHeroes.setLayoutManager(new LinearLayoutManager(this));
        liveData.getMutableLiveData().observe(this, new Observer<List<UserDetail>>() {
            @Override
            public void onChanged(List<UserDetail> userDetails) {
                String[] heroes = new String[userDetails.size()];

               /* for (UserDetail users : userDetails) {
                    new saveData().execute(users);

                }
*/
                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < userDetails.size(); i++) {
                    heroes[i] = userDetails.get(i).getName();
                    addresses.add(userDetails.get(i).getAddress());
                }

                MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(MainActivity.this, userDetails);

                binding.listViewHeroes.setAdapter(adapter);
                //listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, userDetail.getName()));
            }
        });
        //calling the method to display the heroes
        CallAPIParalley();
        //new getData().execute();
    }

    class saveData extends AsyncTask<UserDetail, Void, Void> {
        @Override
        protected Void doInBackground(UserDetail... userDetails) {
            cliet.insert(userDetails[0]);
            return null;
        }
    }

    class getData extends AsyncTask<Void, Void, List<UserDetail>> {
        @Override
        protected void onPostExecute(List<UserDetail> userDetails) {
            super.onPostExecute(userDetails);
        }

        @Override
        protected List<UserDetail> doInBackground(Void... voids) {

            return cliet.getall();
        }

    }

    private void CallAPIParalley() {
        Observable<List<UserDetail>> call = RetrofitClient.getInstance().getMyApi().getuserDetails();
        Observable<List<Hero>> heroes = RetrofitClient.getInstance().getMyApi().getHeroes();

        Observable<List<String>> result =
                Observable.zip(call.subscribeOn(Schedulers.io()).onErrorReturnItem(null), heroes.subscribeOn(Schedulers
                        .io()), new BiFunction<List<UserDetail>, List<Hero>, List<String>>() {
                    @Override
                    public List<String> apply(List<UserDetail> type1, List<Hero> type2) {
                        List<String> list = new ArrayList();
                        list.add(type1.toString());
                        list.add(type2.toString());
                        return list;
                    }
                });

        result.observeOn(AndroidSchedulers.mainThread())
                .retry(2)
                .subscribeWith(new DisposableObserver<List<String>>() {

                    @Override
                    public void onNext(List<String> s) {
                        Log.d("Result", s.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Result", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void CallAPISequencly() {
        Observable<List<UserDetail>> call = RetrofitClient.getInstance().getMyApi().getuserDetails();
        Observable<List<Hero>> heroes = RetrofitClient.getInstance().getMyApi().getHeroes();

        List<String> result = new ArrayList<>();
        Disposable disposable = call
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<UserDetail>, ObservableSource<List<Hero>>>) response1 -> {
                    result.add(response1.toString());
                    return heroes;
                })
                /*.flatMap((Function<ResponseType2, ObservableSource<ResponseType3>>) response2 -> {
                    result.add(response2.data);
                    return retrofit.getApi_c();
                })*/
                .map(response3 -> {
                    result.add(response3.toString());
                    return response3;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Hero>>() {

                    @Override
                    public void onNext(List<Hero> response3) {
                        Log.d("Result", "result variable will have all the data");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void callAPiUsingRetrofit() {
        Observable<List<UserDetail>> call = RetrofitClient.getInstance().getMyApi().getuserDetails();
        //Combined! Do something fancy here.)

        call.subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<UserDetail>>() {

                    @Override
                    public void onNext(List<UserDetail> value) {
                        liveData.setMutableLiveData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
    @SuppressLint("CheckResult")
    private void callAPiUsingRetrofitAndRxjava() {

        //Combined! Do something fancy here.)
        Call<List<UserDetail>> call = RetrofitClient.getInstance().getMyApi().getuserDetails1();
        call.enqueue(new Callback<List<UserDetail>>() {
            @Override
            public void onResponse(Call<List<UserDetail>> call, Response<List<UserDetail>> response) {
                List<UserDetail> userDetails = response.body();
                liveData.setMutableLiveData(userDetails);

            }


            @Override
            public void onFailure(Call<List<UserDetail>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
