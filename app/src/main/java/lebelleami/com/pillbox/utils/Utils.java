package lebelleami.com.pillbox.utils;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lebelleami.com.pillbox.controller.AddMedicationActivity;

public class Utils {
    Utils(){}

    //get time interval to trigger alarm manager
    public static int getTimeInterval(String getInterval) {
        int interval = Integer.parseInt(getInterval);//convert string interval into integer
        //Return interval on basis of radio button selection
        if (interval == 1)
            return interval * 1000;
        else return 0;
    }

    public static int getDaysDifference() throws ParseException {
            //date difference for alarm
            String d1 = AddMedicationActivity.startDay;
            String d2 = AddMedicationActivity.endDay;
            long difference;
            long  diffDays;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            Date date1 = simpleDateFormat.parse(d1);
            Date date2 = simpleDateFormat.parse(d2);
                difference = Math.abs(date1.getTime() - date2.getTime());
                diffDays = difference / (24 * 60 * 60 * 1000);

        return (int) diffDays;
    }


    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }


    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }
}
