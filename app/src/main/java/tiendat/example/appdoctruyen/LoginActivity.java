package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiDangNhap;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.DangNhap;
import tiendat.example.appdoctruyen.object.TruyenTranh;
import tiendat.example.appdoctruyen.object.User;

public class LoginActivity extends AppCompatActivity implements DangNhap {
    private final int INTERNET_PERMISSION = 1;
    private final int READ_PERMISSION = 3;
    private final int WRITE_PERMISSION = 2;
    EditText id, password;
    Button login , regis;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission(Manifest.permission.INTERNET , INTERNET_PERMISSION);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE , WRITE_PERMISSION);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE , READ_PERMISSION);

        id = findViewById(R.id.txtID);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnLogin);
        regis = findViewById(R.id.btnRegis);

        sharedPreferences = getApplicationContext().getSharedPreferences("userData" , getApplicationContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkLogin();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!id.getText().toString().matches("") && !password.getText().toString().matches("")){
                    new ApiDangNhap(LoginActivity.this , id.getText().toString() , password.getText().toString()).execute();
                }else {
                    Toast.makeText(LoginActivity.this , "Không được để trống dữ liệu" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisActivity.class);
                startActivity(intent);
            }
        });


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

            editor.putString("id" , user.getId());
            editor.putString("password" , user.getPasswword());
            editor.apply();

            startActivity(intent);
        }else {
            Toast.makeText(this , "Sai tên đăng nhập hoặc mật khẩu hoặc lỗi kết nối" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void biLoi() {
        if (sharedPreferences.contains("id")){
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this , "Sai tên đăng nhập hoặc mật khẩu hoặc lỗi kết nối" , Toast.LENGTH_SHORT).show();
        }
    }

    public void checkLogin() {

        if (sharedPreferences.contains("id")) {
            String id = sharedPreferences.getString("id", null);
            String password = sharedPreferences.getString("password", null);
            new ApiDangNhap(this, id, password).execute();
        }
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[] { permission }, requestCode);
        }else {
            Log.d("TAG1432", "checkPermission: permission already ok");
        }
    }

}