package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import tiendat.example.appdoctruyen.interfaces.DangNhap;
import tiendat.example.appdoctruyen.object.TruyenTranh;
import tiendat.example.appdoctruyen.object.User;

public class LoginActivity extends AppCompatActivity implements DangNhap {
    EditText id, password;
    Button login , regis;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.txtID);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnLogin);
        regis = findViewById(R.id.btnRegis);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString() != "" && password.getText().toString() != ""){
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
    public void ketThuc(String data) {
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
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this , "Sai tên đăng nhập hoặc mật khẩu" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this , "Sai tên đăng nhập hoặc mật khẩu" , Toast.LENGTH_SHORT).show();
    }

}