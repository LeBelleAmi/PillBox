package lebelleami.com.pillbox.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import lebelleami.com.pillbox.model.Medication;


@Database(entities = {Medication.class}, version = 1, exportSchema = false)
public abstract class MedicationDatabase extends RoomDatabase {

    private static final String LOG_TAG = MedicationDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "medicationlist";
    private static MedicationDatabase sInstance;

    public static MedicationDatabase getsInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), MedicationDatabase.class, MedicationDatabase.DATABASE_NAME )
                        .build();
            }
        }
        Log.d(LOG_TAG, "getting the database instance");
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public abstract MedicationDAO medicationDAO();

}
