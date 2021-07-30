package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import tiendat.example.appdoctruyen.api.ApiDangKy;
import tiendat.example.appdoctruyen.api.ApiDangNhap;
import tiendat.example.appdoctruyen.interfaces.DangKy;

public class RegisActivity extends AppCompatActivity implements DangKy {

    EditText id , password , number , email , address;
    String base64Avatar = null;
    ImageView avatar;
    Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        id = findViewById(R.id.txt_regis_ID);
        password = findViewById(R.id.txt_regis_Password);
        number = findViewById(R.id.txt_regis_Number);
        email = findViewById(R.id.txt_regis_Email);
        address = findViewById(R.id.txt_regis_Address);
        avatar = findViewById(R.id.txt_regis_Avatar);
        regis = findViewById(R.id.btn_regis_Register);

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ava_flurry);

        avatar.setImageBitmap(resizeBitmap(icon));

        //base64Avatar = encodeBase64(resizeBitmap(icon));

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pushId = id.getText().toString();
                String pushPassword = password.getText().toString();
                String pushNumber = number.getText().toString();
                String pushEmail = email.getText().toString();
                String pushAddress = address.getText().toString();

                if(id.getText().toString() != "" && password.getText().toString() != ""){
                    new ApiDangKy(RegisActivity.this ,
                            pushId ,
                            pushPassword ,
                            pushNumber ,
                            pushEmail ,
                            pushAddress ,
                            base64Avatar).execute();
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
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void biLoi() {

    }

    public Bitmap resizeBitmap(Bitmap bitmap){
        Bitmap des = Bitmap.createScaledBitmap(bitmap , 200 , 200 , true);
        return des;
    }

    public String encodeBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }
}