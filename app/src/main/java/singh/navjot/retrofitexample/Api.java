package singh.navjot.retrofitexample;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import singh.navjot.retrofitexample.model.UserDetail;

/**
 * Created by Belal on 10/2/2017.
 */

public interface Api {

    String BASE_URL = "https://simplifiedcoding.net/demos/";
    String BASE_URL1 = "https://jsonplaceholder.typicode.com/";

    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/
    @GET("todos")
    Observable<List<Hero>> getHeroes();
    @GET("users")
    Observable<List<UserDetail>> getuserDetails();
    @GET("users")
    Call<List<UserDetail>> getuserDetails1();
}


/*
    @Multipart
    @POST("/api/Accounts/editaccount")
    Call<User> editUser(@Header("Authorization") String authorization,
                        @Part("file\"; filename=\"pp.png\" ") RequestBody file,
                        @Part("FirstName") RequestBody fname,
                        @Part("Id") RequestBody id);
*/
/*
File file = new File(imageUri.getPath());

    RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),
            file);

    RequestBody name = RequestBody.create(MediaType.parse("text/plain"),
            firstNameField.getText()
                    .toString());

    RequestBody id = RequestBody.create(MediaType.parse("text/plain"),
            AZUtils.getUserId(this));

    Call<User> call = client.editUser(AZUtils.getToken(this),
            fbody,
            name,
            id);

call.enqueue(new Callback<User>() {

@Override
public void onResponse(retrofit.Response<User> response,
        Retrofit retrofit) {

        AZUtils.printObject(response.body());
        }

@Override
public void onFailure(Throwable t) {

        t.printStackTrace();
        }
        });*/
