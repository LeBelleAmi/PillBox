package lebelleami.com.pillbox.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import lebelleami.com.pillbox.model.Medication;
import lebelleami.com.pillbox.R;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MedicationViewHolder>  {

    private Context ctx;
    private List<Medication> medicationList;
    private OnItemClickListener mOnItemClickListener;

    public MedAdapter(Context context, List<Medication> medicationList) {
        this.ctx = context;
        this.medicationList = medicationList;
        try {
            mOnItemClickListener = ((OnItemClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }


    public class MedicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout lv;
        ImageView drugImage;
        TextView drugName;
        TextView drugAmount;
        TextView drugInstruction;
        TextView drugTime;


        //initialize views of the viewHolder
        MedicationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            lv =  itemView.findViewById(R.id.drug_container);
            drugImage = itemView.findViewById(R.id.drug_image);
            drugName = itemView.findViewById(R.id.drug_title);
            drugAmount = itemView.findViewById(R.id.drug_amount);
            drugInstruction = itemView.findViewById(R.id.drug_instruct);
            drugTime = itemView.findViewById(R.id.drug_notification);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }




    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @NonNull
    @Override
    public MedAdapter.MedicationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drug_item, viewGroup, false);
        return new MedicationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MedAdapter.MedicationViewHolder holder, final int position) {

        final Medication med = medicationList.get(position);
        holder.drugName.setText(med.getMedName());
        holder.drugAmount.setText(med.getDosage());
        holder.drugTime.setText(med.getStartTime());
        holder.drugInstruction.setText(med.getMedInstruction());

        byte[] btcArray = med.getDrugImage();
        final Drawable image = new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(btcArray, 0, btcArray.length));
        holder.drugImage.setImageDrawable(image);
    }


    @Override
    public int getItemCount() {
        if (medicationList == null) {
            return 0;
        } else {
            return medicationList.size();
        }
    }

    public void setItem(List<Medication> medications){
        medicationList = medications;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
