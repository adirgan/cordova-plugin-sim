// MCC and MNC codes on Wikipedia
// http://en.wikipedia.org/wiki/Mobile_country_code

// Mobile Network Codes (MNC) for the international identification plan for public networks and subscriptions
// http://www.itu.int/pub/T-SP-E.212B-2014

// class TelephonyManager
// http://developer.android.com/reference/android/telephony/TelephonyManager.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/TelephonyManager.java

// permissions
// http://developer.android.com/training/permissions/requesting.html

// Multiple SIM Card Support
// https://developer.android.com/about/versions/android-5.1.html

// class SubscriptionManager
// https://developer.android.com/reference/android/telephony/SubscriptionManager.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/SubscriptionManager.java

// class SubscriptionInfo
// https://developer.android.com/reference/android/telephony/SubscriptionInfo.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/SubscriptionInfo.java

// Cordova Permissions API
// https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#android-permissions

package com.pbakondy;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

public class Sim extends CordovaPlugin {
  private static final String GET_SIM_INFO = "getSimInfo";
  private static final String HAS_READ_PERMISSION = "hasReadPermission";
  private static final String REQUEST_READ_PERMISSION = "requestReadPermission";

  private CallbackContext callback;

  @SuppressLint("HardwareIds")
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    callback = callbackContext;
    if (GET_SIM_INFO.equals(action)) {
      try {
        getSimInfo(callbackContext);
        return true;
      } catch (Exception e) {
        callbackContext.error("Error al obtener información de la SIM: " + e.getMessage());
        return false;
      }
    } else if (HAS_READ_PERMISSION.equals(action)) {
      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
      return true;
    } else if (REQUEST_READ_PERMISSION.equals(action)) {
      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
      return true;
    } else {
      callbackContext.error("Acción no reconocida");
      return false;
    }
  }

  @SuppressLint("HardwareIds")
  private void getSimInfo(CallbackContext callbackContext) throws JSONException {
    Context context = this.cordova.getActivity().getApplicationContext();
    TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    String countryCode = manager.getSimCountryIso();
    String carrierName = manager.getSimOperatorName();
    int simState = manager.getSimState();

    JSONObject result = new JSONObject();
    result.put("carrierName", carrierName);
    result.put("countryCode", countryCode);
    result.put("simState", simState);

    callbackContext.success(result);
  }

  @Override
  public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException
  {
    if (true) {
      this.callback.success();
    } else {
      this.callback.error("Permission denied");
    }
  }
}