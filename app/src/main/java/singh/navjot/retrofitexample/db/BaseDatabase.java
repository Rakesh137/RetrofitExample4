package singh.navjot.retrofitexample.db;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import singh.navjot.retrofitexample.model.UserDetail;

@androidx.room.Database(entities = {UserDetail.class},version = 1)
@TypeConverters({Converters.class,Converters.ConvertersCompnay.class})
public abstract class BaseDatabase extends RoomDatabase {
    public abstract Database baseDabase();
}
