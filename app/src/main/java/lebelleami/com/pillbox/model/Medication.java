package lebelleami.com.pillbox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "medication")
public class Medication implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] drugImage;

    @ColumnInfo(name = "medication_name")
    private String medName;

    private String description, startDate, startTime, endDate, endTime, dosage, medInstruction, doctor;

    private int frequency;

    @Ignore
    public Medication(){}


    public Medication(long id, byte[] drugImage, String medName, String description, int frequency, String startDate, String startTime, String endDate, String endTime, String dosage, String medInstruction, String doctor) {
        this.id = id;
        this.drugImage = drugImage;
        this.medName = medName;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.dosage = dosage;
        this.medInstruction = medInstruction;
        this.doctor = doctor;
    }

    protected Medication(Parcel in)
    {
        id = in.readLong();
        drugImage = in.createByteArray();
        medName = in.readString();
        description = in.readString();
        frequency = in.readInt();
        startDate = in.readString();
        startTime = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        dosage = in.readString();
        medInstruction = in.readString();
        doctor = in.readString();

    }

    public static final Parcelable.Creator<Medication> CREATOR = new Parcelable.Creator<Medication>()
    {
        @Override
        public Medication createFromParcel(Parcel in)
        {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size)
        {
            return new Medication[size];
        }
    };




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getDrugImage() {
        return drugImage;
    }

    public void setDrugImage(byte[] drugImage) {
        this.drugImage = drugImage;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getMedInstruction() {
        return medInstruction;
    }

    public void setMedInstruction(String medInstruction) {
        this.medInstruction = medInstruction;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(id);
        parcel.writeByteArray(drugImage);
        parcel.writeString(medName);
        parcel.writeString(description);
        parcel.writeInt(frequency);
        parcel.writeString(startDate);
        parcel.writeString(startTime);
        parcel.writeString(endDate);
        parcel.writeString(endTime);
        parcel.writeString(dosage);
        parcel.writeString(medInstruction);
        parcel.writeString(doctor);

    }
}
