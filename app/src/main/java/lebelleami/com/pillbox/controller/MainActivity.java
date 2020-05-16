package lebelleami.com.pillbox.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import lebelleami.com.pillbox.database.MedicationDatabase;
import lebelleami.com.pillbox.model.Medication;
import lebelleami.com.pillbox.R;
import lebelleami.com.pillbox.view.MedAdapter;
import lebelleami.com.pillbox.utils.Utils;

public class MainActivity extends AppCompatActivity implements MedAdapter.OnItemClickListener {

    Toolbar toolbar;
    RecyclerView medsRecyclerView;
    MedAdapter medAdapter;
    List<Medication> medicationList;
    LinearLayout layoutDocApp, layoutVitals, layoutMeds, buttonFrame;
    FrameLayout fabFrameLayout;
    FloatingActionButton fabMenu, fabDocApp, fabVitals, fabMeds;
    CardView cardView;
    AppCompatTextView dateText, emptyView;
    AppCompatImageButton editProfile;
    private boolean fabExpanded = false;
    private MedicationDatabase medicationDatabase;
    int pos;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        medicationDatabase = MedicationDatabase.getsInstance(MainActivity.this);

        fabMenu = this.findViewById(R.id.fabMenu);
        fabDocApp = this.findViewById(R.id.fabDocApp);
        fabVitals = this.findViewById(R.id.fabVitals);
        fabMeds = this.findViewById(R.id.fabDrugs);
        cardView = findViewById(R.id.profileCard);
        dateText = findViewById(R.id.dateText);
        editProfile = findViewById(R.id.editProfile);
        emptyView = findViewById(R.id.emptyview);


        buttonFrame = this.findViewById(R.id.mainFrame);
        layoutDocApp = this.findViewById(R.id.layoutFabappointment);
        layoutVitals = this.findViewById(R.id.layoutFabVitals);
        layoutMeds = this.findViewById(R.id.layoutFabDrugs);
        fabFrameLayout = this.findViewById(R.id.fabFrame);

        //set up system calender and time
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        dateText.setText(date);

        //app toolbar
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            Utils.setSystemBarColor(this, android.R.color.white);
            Utils.setSystemBarLight(this);
        }


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Profile", Toast.LENGTH_LONG).show();
            }
        });

        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();


        //extra fab buttons
        fabDocApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Doc Appointment", Toast.LENGTH_LONG).show();
            }
        });

        fabVitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Vitals", Toast.LENGTH_LONG).show();
            }
        });

        fabMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Add Medication", Toast.LENGTH_LONG).show();
                Intent startActivity = new Intent(MainActivity.this, AddMedicationActivity.class);
                startActivity(startActivity);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Edit Profile", Toast.LENGTH_LONG).show();
            }
        });

        initializeView();
        displayList();

    }





    private void displayList(){
// initialize database instance
        medicationDatabase = MedicationDatabase.getsInstance(MainActivity.this);
// fetch list of medications in background thread
        new RetrieveTask(MainActivity.this).execute();
    }

    @Override
    public void onItemClick(final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.med_details_dialog, null);
        builder.setView(view);

        byte[] btcArray = medicationList.get(position).getDrugImage();
        final Drawable image = new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(btcArray, 0, btcArray.length));

        AppCompatImageView imageView = view.findViewById(R.id.drug_det_avatar);
        imageView.setImageDrawable(image);
        AppCompatTextView tvMedName = view.findViewById(R.id.med_det_text_view);
        tvMedName.setText(medicationList.get(position).getMedName());
        AppCompatTextView tvMedDesc = view.findViewById(R.id.descrip_det_text_view);
        tvMedDesc.setText(medicationList.get(position).getDescription());
        AppCompatTextView tvMedFreq = view.findViewById(R.id.freq_det_text_view);
        tvMedFreq.setText(String.valueOf(medicationList.get(position).getFrequency()));
        AppCompatTextView tvStartDate = view.findViewById(R.id.startdate_det_text_view);
        tvStartDate.setText(medicationList.get(position).getStartDate());
        AppCompatTextView tvStartTime = view.findViewById(R.id.starttime_det_text_view);
        tvStartTime.setText(medicationList.get(position).getStartTime());
        AppCompatTextView tvEndDate = view.findViewById(R.id.enddate_det_text_view);
        tvEndDate.setText(medicationList.get(position).getEndDate());
        AppCompatTextView tvEndTime = view.findViewById(R.id.endtime_det_text_view);
        tvEndTime.setText(medicationList.get(position).getEndTime());
        AppCompatTextView tvDosage = view.findViewById(R.id.dosage_det_text_view);
        tvDosage.setText(medicationList.get(position).getDosage());
        AppCompatTextView tvMedInstr = view.findViewById(R.id.dosageinst_det_text_view);
        tvMedInstr.setText(medicationList.get(position).getMedInstruction());
        AppCompatTextView tvDoctor = view.findViewById(R.id.doctor_det_text_view);
        tvDoctor.setText(medicationList.get(position).getDoctor());

        final AlertDialog alertDialog = builder.create();

        final MaterialButton edit = view.findViewById(R.id.bt_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(getApplicationContext(), AddMedicationActivity.class).putExtra("meds", medicationList.get(position));
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(editIntent);
            }
        });

        MaterialButton delete = view.findViewById(R.id.bt_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new  AlertDialog.Builder(MainActivity.this);
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_delete, null);
                builder.setView(view);

                final AlertDialog deleteAlertDialog = builder.create();

                MaterialButton deletePill = view.findViewById(R.id.bt_positive);
                deletePill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DeleteTask(MainActivity.this, medicationList.get(position)).execute();
                        medicationList.remove(pos);
                        Toast.makeText(MainActivity.this, "med data deleted", Toast.LENGTH_LONG).show();
                        deleteAlertDialog.dismiss();
                        alertDialog.dismiss();
                    }
                });

                MaterialButton cancelDelete = view.findViewById(R.id.bt_negative);
                cancelDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       deleteAlertDialog.dismiss();
                    }
                });
                deleteAlertDialog.show();
            }

        });

        AppCompatImageButton close = view.findViewById(R.id.bt_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    //retrieve all medications in the background thread
    private static class RetrieveTask extends AsyncTask<Void,Void,List<Medication>>{

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Medication> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().medicationDatabase.medicationDAO().loadAllMedications();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Medication> medications) {
            if (medications!=null && medications.size()>0 ){
                activityReference.get().medicationList = medications;

                // hides empty text view
                activityReference.get().emptyView.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().medAdapter = new MedAdapter(activityReference.get(), medications);
                activityReference.get().medsRecyclerView.setAdapter(activityReference.get().medAdapter);


            }
        }

    }

    private static class DeleteTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<MainActivity> activityReference;
        private Medication medication;

        // only retain a weak reference to the activity
        DeleteTask(MainActivity context, Medication medication1) {
            activityReference = new WeakReference<>(context);
            this.medication = medication1;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().medicationDatabase.medicationDAO().deleteMedication(medication);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                // calls empty text view
                activityReference.get().listVisibility();
                activityReference.get().medAdapter.notifyDataSetChanged();
            }
        }
    }



    private void initializeView() {

        medsRecyclerView = findViewById(R.id.list_view);
        medsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) medsRecyclerView.getLayoutManager();
        medsRecyclerView.setLayoutManager(linearLayoutManager);
        assert linearLayoutManager != null;
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //medsRecyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(medsRecyclerView);


        medicationList = new ArrayList<>();
        medAdapter = new MedAdapter(MainActivity.this, medicationList);
        medsRecyclerView.setAdapter(medAdapter);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                medicationList.add((Medication) data.getParcelableExtra("medication"));
            }else if( resultCode == 2){
                medicationList.set(pos,(Medication) data.getSerializableExtra("medication"));
            }
            listVisibility();
        }
    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (medicationList.size() == 0){ // no item to display
            if (emptyView.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        emptyView.setVisibility(emptyMsgVisibility);
        medAdapter.notifyDataSetChanged();
    }



    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutMeds.setVisibility(View.INVISIBLE);
        layoutVitals.setVisibility(View.INVISIBLE);
        layoutDocApp.setVisibility(View.INVISIBLE);
        buttonFrame.setBackgroundResource(0);
        fabMenu.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutMeds.setVisibility(View.VISIBLE);
        layoutVitals.setVisibility(View.VISIBLE);
        layoutDocApp.setVisibility(View.VISIBLE);
        buttonFrame.setBackground(getResources().getDrawable(R.color.colorBackground));
        //Change settings icon to 'X' icon
        fabMenu.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        MedicationDatabase.destroyInstance();
    }

}
