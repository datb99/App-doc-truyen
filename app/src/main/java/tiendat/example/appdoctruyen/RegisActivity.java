package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tiendat.example.appdoctruyen.api.ApiDangKy;
import tiendat.example.appdoctruyen.api.ApiDangNhap;
import tiendat.example.appdoctruyen.interfaces.DangKy;

public class RegisActivity extends AppCompatActivity implements DangKy {

    EditText id , password;
    Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        id = findViewById(R.id.txt_regis_ID);
        password = findViewById(R.id.txt_regis_Password);
        regis = findViewById(R.id.btn_regis_Register);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString() != "" && password.getText().toString() != ""){
                    new ApiDangKy(RegisActivity.this , id.getText().toString() , password.getText().toString()).execute();
                }else {
                    Toast.makeText(RegisActivity.this , "Không được để trống dữ liệu" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        Toast.makeText(this , data , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void biLoi() {

    }
}