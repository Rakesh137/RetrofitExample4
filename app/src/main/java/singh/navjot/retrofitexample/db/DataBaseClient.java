package singh.navjot.retrofitexample.db;
import android.content.Context;
import androidx.room.Room;
public class DataBaseClient {
    Context ctx;
    private static DataBaseClient mInstance;
    public BaseDatabase database;

    public DataBaseClient(Context ctx) {
        this.ctx = ctx;
        database = Room.databaseBuilder(ctx,BaseDatabase.class,"mydatabase").build();
    }

    public static synchronized DataBaseClient getInstance(Context ctx) {
        if (mInstance == null)
            mInstance = new DataBaseClient(ctx);
        return mInstance;
    }
}
