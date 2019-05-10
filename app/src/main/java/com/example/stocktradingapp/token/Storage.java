package com.example.stocktradingapp.token;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {
    SharedPreferences sharedpreferences;
    String accessToken;
    Context c;

    public Storage(Context context) {
        this.c = context;
    }

    public String getAccessToken() {
        sharedpreferences = c.getSharedPreferences("AccessToken",
                Context.MODE_PRIVATE);
        accessToken = sharedpreferences.getString("AccessToken", "");
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        sharedpreferences = c.getSharedPreferences("AccessToken",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("AccessToken", accessToken);
        editor.commit();
    }
}
