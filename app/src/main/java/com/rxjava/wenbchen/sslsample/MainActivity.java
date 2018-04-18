package com.rxjava.wenbchen.sslsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.commonsware.cwac.netsecurity.MemorizingTrustManager;
import com.commonsware.cwac.netsecurity.OkHttp3Integrator;
import com.commonsware.cwac.netsecurity.TrustManagerBuilder;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_hello)
    TextView tvHelloWorld;

    private MemorizingTrustManager memo;
    private OkHttpClient ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        tvHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SSLPinningCall().execute();
            }
        });
    }

    private class SSLPinningCall extends AsyncTask<Void, Void, String> {

        String res = "exception thrown";
        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient.Builder okb = new OkHttpClient.Builder();
            TrustManagerBuilder tmb = new TrustManagerBuilder();
            File memoDir = new File(getApplicationContext().getCacheDir(), "ssltestsexample");
            boolean bool = memoDir.mkdirs();

            MemorizingTrustManager.Builder memoBuilder=
                    new MemorizingTrustManager.Builder()
                            .saveTo(memoDir, "helloworld".toCharArray());

             memo = memoBuilder.build();
             tmb.add(memo);

            try {
                OkHttp3Integrator.applyTo(tmb, okb);
                ok=okb.build();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            Request request = new Request.Builder()
                    .url("https://publicobject.com/robots.txt")
                    .build();
            try (Response response = ok.newCall(request).execute()) {
                res = "pin ssl success";
            } catch (IOException e) {
                if (e instanceof SSLHandshakeException) {
                    res = "sslhandshakeexception thrown";
                } else if (e instanceof SSLPeerUnverifiedException) {
                    res = "ssl peer unverified exception thrown";
                }
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            tvHelloWorld.setText(result);
        }
    }
}
