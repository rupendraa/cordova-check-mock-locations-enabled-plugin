package io.dijitaladam.plugins.mocklocation;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings;
import android.os.Build;

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
      try {
	      if(Build.VERSION.SDK_INT < 23){
	          if (Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
	              callbackContext.success(0);
	          } else {
	              callbackContext.success(1);
	          }
	      }
	      else{
	        if(Settings.Global.getInt(this.cordova.getActivity().getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0) == 1)
	            callbackContext.success(2);
	        else
	            callbackContext.success(0);
	      }
      } catch (Exception e) {
        callbackContext.error(e.getMessage());
      }
    }
}
