package tiendat.example.appdoctruyen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tiendat.example.appdoctruyen.api.ApiDangKy;
import tiendat.example.appdoctruyen.api.ApiDangNhap;
import tiendat.example.appdoctruyen.api.ApiUploadAva;
import tiendat.example.appdoctruyen.interfaces.DangKy;
import tiendat.example.appdoctruyen.interfaces.uploadAva;

public class RegisActivity extends AppCompatActivity implements DangKy , uploadAva {

    private final int PICK_IMAGE = 100;
    EditText id , password , number , email , address;
    ImageView avatar;
    Button regis;
    Uri imageUri = null;

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


        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri != null){
                    new ApiUploadAva(RegisActivity.this ,getRealPathFromURI(getApplicationContext() , imageUri) , id.getText().toString()).execute();
                }else {
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
                                "").execute();
                    }else {
                        Toast.makeText(RegisActivity.this , "Không được để trống dữ liệu" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null){
            imageUri = data.getData();
            avatar.setImageURI(imageUri);
        }
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

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void finishUploadAva() {
        String pushId = id.getText().toString();
        String pushPassword = password.getText().toString();
        String pushNumber = number.getText().toString();
        String pushEmail = email.getText().toString();
        String pushAddress = address.getText().toString();
        String img = id.getText().toString() + ".jpg";

        if(id.getText().toString() != "" && password.getText().toString() != ""){
            new ApiDangKy(RegisActivity.this ,
                    pushId ,
                    pushPassword ,
                    pushNumber ,
                    pushEmail ,
                    pushAddress ,
                    img).execute();
        }else {
            Toast.makeText(RegisActivity.this , "Không được để trống dữ liệu" , Toast.LENGTH_SHORT).show();
        }
    }
}