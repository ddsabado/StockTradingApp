package com.example.stocktradingapp.token;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;

import com.example.stocktradingapp.AccessTokenServiceInterface;
import com.example.stocktradingapp.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadAccessTokenTask extends AsyncTask<Void, Void, Void> {
    private static Context mContext;
    private View rootView;

    public DownloadAccessTokenTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getAccessToken(Utils.clientId, Utils.clientSecret);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private static void getAccessToken(String clientId, String clientSecret) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Utils.tokenUrl)
                .build();

        AccessTokenServiceInterface service = retrofit.create(AccessTokenServiceInterface.class);

        //grant types = client_credentials
        Call<TokenResponse> call = service.getToken(clientId, clientSecret, "client_credentials");
        try {
            Response<TokenResponse> response = call.execute();
            new Storage(mContext).setAccessToken(getTokenType(response.body().getTokenType())+" "+response.body().getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getTokenType(String tokenType) {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if (! Character.isUpperCase(tokenType.charAt(0))) {
            tokenType =
                    Character
                            .toString(tokenType.charAt(0))
                            .toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }
}
