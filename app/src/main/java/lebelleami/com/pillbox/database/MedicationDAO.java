package lebelleami.com.pillbox.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lebelleami.com.pillbox.model.Medication;

@Dao
public interface MedicationDAO {

    @Query(" SELECT * FROM  medication ORDER BY id ")
    List<Medication> loadAllMedications();

    @Insert void insertMedication(Medication medication);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateMedication(Medication medication);

    @Delete  void deleteMedication(Medication medication);

    @Delete  void deleteAllMedications(Medication medication);


}
