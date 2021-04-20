package singh.navjot.retrofitexample.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import singh.navjot.retrofitexample.model.UserDetail;
@Dao
public interface Database {
    @Query("SELECT * FROM userdetail")
    List<UserDetail> getall();
    @Insert
    void insert(UserDetail users);

}
