package com.resikin.percakapan.core.authentication.task;//package com.resikin.percakapan.core.authentication.task;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import java.io.IOException;
//
//import static com.resikin.percakapan.utils.DebugConstants.DEBUG_LOGIN;
//
///**
// * Created by andrealeo
// */
//public class bk_RefreshFirebaseInstanceIdTask extends AsyncTask<Object, Object, Void> {
//
//    public bk_RefreshFirebaseInstanceIdTask() {
//        Log.d(DEBUG_LOGIN, "RefreshFirebaseInstanceIdTask");
//    }
//
//    @Override
//    protected Void doInBackground(Object... params) {
//        try {
//            FirebaseInstanceId.getInstance().deleteInstanceId();
//            Log.i(DEBUG_LOGIN, "RefreshFirebaseInstanceIdTask.doInBackground: instanceId deleted with success.");
//
//            // Now manually call onTokenRefresh()
//            Log.d(DEBUG_LOGIN, "RefreshFirebaseInstanceIdTask.doInBackground: Getting new token");
//            FirebaseInstanceId.getInstance().getToken();
//
//        } catch (IOException e) {
//            Log.e(DEBUG_LOGIN, "RefreshFirebaseInstanceIdTask.doInBackground: deleteInstanceIdCatch: " + e.getMessage());
//        }
//
//        return null;
//    }
//}