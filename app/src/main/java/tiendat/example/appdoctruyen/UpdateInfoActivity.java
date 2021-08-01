package tiendat.example.appdoctruyen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import tiendat.example.appdoctruyen.api.ApiDeleteAva;
import tiendat.example.appdoctruyen.api.ApiUpdateInfo;
import tiendat.example.appdoctruyen.api.ApiUploadAva;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.updateInfo;
import tiendat.example.appdoctruyen.interfaces.uploadAva;
import tiendat.example.appdoctruyen.object.User;

public class UpdateInfoActivity extends AppCompatActivity implements updateInfo , uploadAva {

    private final int PICK_IMAGE = 100;
    EditText id , password , number , email , address;
    ImageView avatar;
    Button update;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        id = findViewById(R.id.txt_update_ID);
        password = findViewById(R.id.txt_update_Password);
        number = findViewById(R.id.txt_update_Number);
        email = findViewById(R.id.txt_update_Email);
        address = findViewById(R.id.txt_update_Address);
        avatar = findViewById(R.id.txt_update_Avatar);
        update = findViewById(R.id.btn_update_Register);

        initSetup();

        setClick();

    }

    private void initSetup() {
        User user = global.user;
        id.setText(user.getId());
        password.setText(user.getPasswword());
        number.setText(user.getNumber());
        email.setText(user.getEmail());
        address.setText(user.getAddr());


        String url = "http://"+ global.ip_address +"/public/userava/"+ global.user.getId() +".jpg";

        Glide.with(getApplicationContext())
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(avatar);
    }

    private void setClick() {


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setEnabled(false);
                String id = global.user.getId();
                String pw = password.getText().toString();
                String em = email.getText().toString();
                String num = number.getText().toString();
                String add = address.getText().toString();

                new ApiUpdateInfo(UpdateInfoActivity.this , id , pw , num , em , add).execute();
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
    public void finishDeleteAva() {
        new ApiUploadAva(this , getRealPathFromURI(getApplicationContext() , imageUri) , global.user.getId()).execute();
    }

    @Override
    public void finishUpdateInfo() {
        if (imageUri != null){
            new ApiDeleteAva(UpdateInfoActivity.this , global.user.getId()).execute();
        }
    }

    @Override
    public void finishUploadAva() {
        update.setEnabled(true);
        User user = global.user;
        String pw = password.getText().toString();
        String em = email.getText().toString();
        String num = number.getText().toString();
        String add = address.getText().toString();
        user.setAddr(add);
        user.setEmail(em);
        user.setPasswword(pw);
        user.setNumber(num);
        global.user = user;

    }
}