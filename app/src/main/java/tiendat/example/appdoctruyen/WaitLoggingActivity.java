package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tiendat.example.appdoctruyen.api.ApiDangNhap;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.DangNhap;
import tiendat.example.appdoctruyen.object.User;

public class WaitLoggingActivity extends AppCompatActivity implements DangNhap {

    TextView textView;
    ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    User user;
    boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_logging);

        textView = findViewById(R.id.logging_textView);
        progressBar = findViewById(R.id.logging_progressbar);

        thread.start();

        sharedPreferences = getApplicationContext().getSharedPreferences("userData" , getApplicationContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkLogin();
    }

    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                final int[] count = {0};
                while (!isFinish) {

                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (count[0]){
                                case 0:
                                    textView.setText("logging in.");
                                    count[0]++;
                                    break;
                                case 1:
                                    textView.setText("logging in..");
                                    count[0]++;
                                    break;
                                case 2:
                                    textView.setText("logging in...");
                                    count[0] = 0;
                                    break;
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {

            }
        }
    };

    public void checkLogin() {

        if (sharedPreferences.contains("id")) {
            String id = sharedPreferences.getString("id", null);
            String password = sharedPreferences.getString("password", null);
            new ApiDangNhap(this, id, password).execute();
        }else {
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            isFinish = true;
            this.finish();
        }
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThucLayUser(String data) {
        try {
            user = null;
            JSONArray arr = new JSONArray(data);
            for(int i = 0 ; i < arr.length() ; i ++){
                JSONObject o = arr.getJSONObject(i);
                user = new User(o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(user != null){
            global.user = user;
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            isFinish = true;
            this.finish();
        }else {
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            isFinish = true;
            this.finish();
        }
    }

    @Override
    public void biLoi() {
        if (sharedPreferences.contains("id")){
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            isFinish = true;
            this.finish();
        }else {
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
            isFinish = true;
            this.finish();
        }
    }
}