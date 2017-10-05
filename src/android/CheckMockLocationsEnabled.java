package io.dijitaladam.plugins.mocklocation;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.os.Build;

import java.util.List;

public class CheckMockLocationsEnabled extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("check")) {
            this.check(callbackContext);
            return true;
        }
        return false;

    }

    private void check(CallbackContext callbackContext) {
		
		JSONObject item = new JSONObject();
		JSONArray array = new JSONArray();
		
      try {
	      if(Build.VERSION.SDK_INT < 23){
	         
			 if (Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
				  item.put("active", false);  
	              callbackContext.success(item.toString());
	          } else {
				  item.put("active", true);  
	              callbackContext.success(item.toString());
	          }
	      }
	      else{
         
              Context context=this.cordova.getActivity().getApplicationContext();
			  boolean mockStatus = false;

              PackageManager pm = context.getPackageManager();
              List<ApplicationInfo> packages =
                      pm.getInstalledApplications(PackageManager.GET_META_DATA);

              for (ApplicationInfo applicationInfo : packages) {
                  try {
                      PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                              PackageManager.GET_PERMISSIONS);

                      // Get Permissions
                      String[] requestedPermissions = packageInfo.requestedPermissions;

                      if (requestedPermissions != null) {
                          for (int i = 0; i < requestedPermissions.length; i++) {
                              if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION")
                                      && !applicationInfo.packageName.equals(context.getPackageName())) {
										  
										mockStatus = true;
										array.put(applicationInfo.loadLabel(pm));
                              }
                          }
                      }
                  } catch (PackageManager.NameNotFoundException e) {
                      callbackContext.error(e.getMessage());
                  }
              }
			  
			  if(mockStatus == true){
				item.put("active", true);
				
				try {
					item.put("appName", array);
				} catch (JSONException e) {
					callbackContext.error(e.getMessage());
				}
				
				callbackContext.success(item.toString());
			  }
			  else{
				item.put("active", false);
				callbackContext.success(item.toString());
			  }

	      }
      } catch (Exception e) {
		callbackContext.error(e.getMessage());
      }
    }
}
