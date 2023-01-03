package com.example.siagacovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_sembuh,tv_positif,tv_rawat,tv_meninggal;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

        tv_sembuh = findViewById(R.id.tv_sembuh);
        tv_positif = findViewById(R.id.tv_positif);
        tv_rawat = findViewById(R.id.tv_rawat);
        tv_meninggal = findViewById(R.id.tv_meninggal);

        tampilData();
    }

    private void tampilData(){
        loading = ProgressDialog.show(MainActivity.this, "Memuat Data", "Harap tunggu..");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://bebashosting.com/covid/";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    String sembuh = jo.getString("sembuh");
                    String positif = jo.getString("positif");
                    String dirawat = jo.getString("dirawat");
                    String meninggal = jo.getString("meninggal");

                    tv_sembuh.setText(sembuh);
                    tv_positif.setText(positif);
                    tv_rawat.setText(dirawat);
                    tv_meninggal.setText(meninggal);
                    loading.cancel();
                    Toast.makeText(MainActivity.this,"Berhasil Memuat", Toast.LENGTH_SHORT).show();




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Toast.makeText(MainActivity.this,"Gagal Ambil Rest API", Toast.LENGTH_SHORT);
            }
        }
        );
        queue.add(stringRequest);
    }
}