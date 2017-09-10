package net.ivdata.taskerauto;
/**
 * Created by MystX on 2/09/2017.
 */
import com.google.android.apps.auto.sdk.*;

public class MyCarActivityService extends CarActivityService {

    @Override
    public Class<? extends CarActivity> getCarActivity() {
        return MainCarActivity.class;
    }
}
