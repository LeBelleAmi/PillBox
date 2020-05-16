package lebelleami.com.pillbox.controller;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Calendar;

import lebelleami.com.pillbox.database.MedicationDatabase;
import lebelleami.com.pillbox.model.Medication;
import lebelleami.com.pillbox.R;
import lebelleami.com.pillbox.alarm.AlarmReceiver;
import lebelleami.com.pillbox.utils.Utils;

public class AddMedicationActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Dialog imageDialog;

    TextInputEditText medName, medDescription, medFreq, medDosage, medInstruction, medDoctor;

    AppCompatTextView startDate, endDate, startTime, endTime;

    AppCompatImageButton addMedImage;

    MaterialButton reminderButton;

    AppCompatImageView medImage, imageOne, imageTwo, imageThree, imageFour, imageFive, imageSix,
            imageSeven, imageEight, imageNine, imageTen, imageEleven, imageTwelve, imageThirteen, imageFourteen;

    Toolbar toolbarTwo;

    int currentHour, day, month, mYear, currentMinute, medFrequency;
    public  static String drugName, startDay, endDay, startTimeHr, endTimeHr;

    //Pending intent instance
    private PendingIntent pendingIntent;
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;


    private Medication medication;
    private MedicationDatabase medicationDatabase;

    boolean update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        medicationDatabase = MedicationDatabase.getsInstance(AddMedicationActivity.this);

        imageDialog = new Dialog(this);


        medName = findViewById(R.id.medicname_text_view);
        medDescription = findViewById(R.id.descrip_text_view);
        medFreq = findViewById(R.id.freq_text_view);
        medDosage = findViewById(R.id.dosage_text_view);
        medInstruction = findViewById(R.id.dosageinst_text_view);
        medDoctor = findViewById(R.id.doctor_text_view);
        startDate = findViewById(R.id.startdate_text_view);
        endDate = findViewById(R.id.enddate_text_view);
        startTime = findViewById(R.id.starttime_text_view);
        endTime = findViewById(R.id.endtime_text_view);

        medImage = findViewById(R.id.drug_avatar);
        imageOne = findViewById(R.id.imgone);
        imageTwo = findViewById(R.id.imgtwo);
        imageThree = findViewById(R.id.imgthree);
        imageFour = findViewById(R.id.imgfour);
        imageFive = findViewById(R.id.imgfive);
        imageSix = findViewById(R.id.imgsix);
        imageSeven = findViewById(R.id.imgseven);
        imageEight = findViewById(R.id.imgeight);
        imageNine = findViewById(R.id.imgnine);
        imageTen = findViewById(R.id.imgten);
        imageEleven = findViewById(R.id.imgeleven);
        imageTwelve = findViewById(R.id.imgtwelve);
        imageThirteen = findViewById(R.id.imgthirteen);
        imageFourteen = findViewById(R.id.imgfourteen);

        reminderButton = findViewById(R.id.reminder_button);


        addMedImage = findViewById(R.id.change_avatar);


        //app toolbar
        //appBarLayoutTwo = findViewById(appbartwo);
        toolbarTwo = findViewById(R.id.toolbartwo);
        if (toolbarTwo != null) {
            setSupportActionBar(toolbarTwo);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            if((medication = getIntent().getParcelableExtra("meds")) != null){
                getSupportActionBar().setTitle("Update Medication");
                update = true;
                byte[] btcArray = medication.getDrugImage();
                Drawable image = new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(btcArray, 0, btcArray.length));
                medImage.setImageDrawable(image);
                medName.setText(medication.getMedName());
                medDescription.setText(medication.getDescription());
                medFreq.setText(String.valueOf(medication.getFrequency()));
                medDosage.setText(medication.getDosage());
                medInstruction.setText(medication.getMedInstruction());
                medDoctor.setText(medication.getDoctor());
                startDate.setText(medication.getStartDate());
                endDate.setText(medication.getEndDate());
                startTime.setText(medication.getStartTime());
                endTime.setText(medication.getEndTime());

            }else {
                getSupportActionBar().setTitle("Add Medication");
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addMedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromDialog();
            }
        });


        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);



        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddMedicationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                String medStartDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
                                startDate.setText(medStartDate);

                                day = calendar.get(Calendar.DAY_OF_MONTH);
                                month = calendar.get(Calendar.MONTH);
                                mYear = calendar.get(Calendar.YEAR);
                            }
                        }, mYear, month, day);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddMedicationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                String medStartDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
                                endDate.setText(medStartDate);

                                day = calendar.get(Calendar.DAY_OF_MONTH);
                                month = calendar.get(Calendar.MONTH);
                                mYear = calendar.get(Calendar.YEAR);
                            }
                        }, mYear, month, day);
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //time picker Dialog

                timePickerDialog = new TimePickerDialog(AddMedicationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        String medStartTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                        startTime.setText(medStartTime);
                    }
                } , calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //time picker Dialog

                timePickerDialog = new TimePickerDialog(AddMedicationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        String medStartTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                        endTime.setText(medStartTime);
                    }
                } , calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Retrieve a PendingIntent that will perform a broadcast */
                Intent alarmIntent = new Intent(AddMedicationActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AddMedicationActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
                String getFrequency = medFreq.getText().toString().trim();
                drugName = medName.getText().toString().trim();
                startDay = startDate.getText().toString().trim();
                endDay = endDate.getText().toString().trim();
                startTimeHr = startTime.getText().toString().trim();
                endTimeHr = endTime.getText().toString().trim();
                //check interval should not be empty and 0
                if (!getFrequency.equals("") && !getFrequency.equals("0"))
                    //finally trigger alarm manager
                    triggerAlarmManager(Utils.getTimeInterval(getFrequency));
            }
        });


    }

    //Trigger alarm manager with entered time interval
    public void triggerAlarmManager(int alarmTriggerTime) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, alarmTriggerTime);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+ alarmTriggerTime, pendingIntent);//set alarm manager with entered timer by converting into milliseconds
    }


    private void pickImageFromDialog() {

        imageDialog.setContentView(R.layout.layout_med_image);

        Button positiveButton = imageDialog.findViewById(R.id.ok_button);
        Button negativeButton = imageDialog.findViewById(R.id.cancel_button);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });

        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageDialog.show();
        imageDialog.setCanceledOnTouchOutside(false);

    }

    public void addImageToView(View view) {
        switch (view.getId()) {

            case R.id.imgone:

                medImage.setImageResource(R.drawable.pill);

                break;

            case R.id.imgtwo:

                medImage.setImageResource(R.drawable.tablet1);

                break;
            case R.id.imgthree:

                medImage.setImageResource(R.drawable.capsulepill);

                break;

            case R.id.imgfour:

                medImage.setImageResource(R.drawable.capsules2);

                break;

            case R.id.imgfive:

                medImage.setImageResource(R.drawable.capsulepills);

                break;
            case R.id.imgsix:

                medImage.setImageResource(R.drawable.tablet2);

                break;

            case R.id.imgseven:

                medImage.setImageResource(R.drawable.herbal);

                break;

            case R.id.imgeight:

                medImage.setImageResource(R.drawable.sleeping);

                break;

            case R.id.imgnine:

                medImage.setImageResource(R.drawable.omega);

                break;

            case R.id.imgten:

                medImage.setImageResource(R.drawable.drugset);

                break;

            case R.id.imgeleven:

                medImage.setImageResource(R.drawable.drops);

                break;

            case R.id.imgtwelve:

                medImage.setImageResource(R.drawable.inhalator);

                break;

            case R.id.imgthirteen:

                medImage.setImageResource(R.drawable.injection);

                break;

            case R.id.imgfourteen:

                medImage.setImageResource(R.drawable.firstaid);

                break;
        }
    }


    //setup activity menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_med:
                if(update){
                    //update meds in database
                    updateMedicationToDB();
                    clean();
                    backToDashboard();
                }else {

                    //add med to database
                    addMedicationToDB();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //check database entries if they are completed
    private boolean checkEntries() {

        boolean check = true;

        if (TextUtils.isEmpty(medName.getText().toString().trim())) {
            medName.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter medication name", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medName.setError(null);
        }

        if (TextUtils.isEmpty(medDescription.getText().toString().trim())) {
            medDescription.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter medication description", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medDescription.setError(null);
        }

        if (TextUtils.isEmpty(medFreq.getText().toString().trim())) {
            medFreq.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter drug frequency", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medFreq.setError(null);
        }

        if (TextUtils.isEmpty(startDate.getText().toString().trim())) {
            startDate.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Select a medication start date", Toast.LENGTH_SHORT).show();
            check = false;
        } else{
           startDate.setError(null);
        }

        if (TextUtils.isEmpty(startTime.getText().toString().trim())) {
            startTime.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Select a medication start time", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            startTime.setError(null);
        }

        if (TextUtils.isEmpty(endDate.getText().toString().trim())) {
            endDate.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Select a medication start date", Toast.LENGTH_SHORT).show();
            check = false;
        } else{
            endDate.setError(null);
        }

        if (TextUtils.isEmpty(endTime.getText().toString().trim())) {
            endTime.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Select a medication end time", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            endTime.setError(null);
        }

        if (TextUtils.isEmpty(medDosage.getText().toString().trim())) {
            medDosage.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter a medication dosage", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medDosage.setError(null);
        }

        if (TextUtils.isEmpty(medInstruction.getText().toString().trim())) {
            medInstruction.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter a medication instruction", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medInstruction.setError(null);
        }

        if (TextUtils.isEmpty(medDoctor.getText().toString().trim())) {
            medDoctor.setError("Field is required.");
            Toast.makeText(AddMedicationActivity.this, "Enter a medication doctor", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            medDoctor.setError(null);
        }


        return check;
    }



    private void addMedicationToDB() {

        //convert medImage to byteArray
        medImage.setDrawingCacheEnabled(true);
        medImage.buildDrawingCache();
        Bitmap bitmap = medImage.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        if(!checkEntries()){
            Toast.makeText(AddMedicationActivity.this, "Your medication details are not complete, please enter all details", Toast.LENGTH_SHORT).show();
        }else {
            medFrequency = Integer.parseInt(medFreq.getText().toString().trim());

            medication = new Medication( 0, data, medName.getText().toString().trim(),
                    medDescription.getText().toString().trim(), medFrequency, startDate.getText().toString().trim(),
                    startTime.getText().toString().trim(), endDate.getText().toString().trim(), endTime.getText().toString().trim(),
                    medDosage.getText().toString().trim(), medInstruction.getText().toString().trim(), medDoctor.getText().toString().trim());

            // create worker thread to insert data into database
            new InsertTask(AddMedicationActivity.this, medication).execute();

            Toast.makeText(this, "med data added", Toast.LENGTH_LONG).show();

            clean();
            backToDashboard();
        }

    }


    private void setResult(Medication medication){
        setResult(1,new Intent().putExtra("medication", medication));
        finish();
    }


    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddMedicationActivity> activityReference;
        private Medication medication;

        // only retain a weak reference to the activity
        InsertTask(AddMedicationActivity context, Medication medication1) {
            activityReference = new WeakReference<>(context);
            this.medication = medication1;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().medicationDatabase.medicationDAO().insertMedication(medication);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(medication);
            }
        }

    }


    private void updateMedicationToDB() {
        medImage.setDrawingCacheEnabled(true);
        medImage.buildDrawingCache();
        Bitmap bitmap = medImage.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        medFrequency = Integer.parseInt(medFreq.getText().toString().trim());

        medication.setDrugImage(data);
        medication.setMedName(medName.getText().toString().trim());
        medication.setDescription(medDescription.getText().toString().trim());
        medication.setFrequency(medFrequency);
        medication.setStartDate(startDate.getText().toString().trim());
        medication.setStartTime(startTime.getText().toString().trim());
        medication.setEndDate(endDate.getText().toString().trim());
        medication.setEndTime(endTime.getText().toString().trim());
        medication.setDosage(medDosage.getText().toString().trim());
        medication.setMedInstruction(medInstruction.getText().toString().trim());
        medication.setDoctor(medDoctor.getText().toString().trim());

        // create worker thread to insert data into database
        new UpdateTask(AddMedicationActivity.this, medication).execute();

        Toast.makeText(this, "med data updated", Toast.LENGTH_LONG).show();
    }

    private static class UpdateTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddMedicationActivity> activityReference;
        private Medication medication;

        // only retain a weak reference to the activity
        UpdateTask(AddMedicationActivity context, Medication medication1) {
            activityReference = new WeakReference<>(context);
            this.medication = medication1;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().medicationDatabase.medicationDAO().updateMedication(medication);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(medication);
            }
        }

    }



    private void clean(){
        medName.setText("");
        medDescription.setText("");
        medFreq.setText("");
        medDosage.setText("");
        medInstruction.setText("");
        medDoctor.setText("");
        startDate.setText("");
        endDate.setText("");
        startTime.setText("");
        endTime.setText("");

    }


    private void backToDashboard(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        MedicationDatabase.destroyInstance();
    }


}

