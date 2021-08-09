package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.uploadAva;

public class ApiUploadAva extends AsyncTask<Void, Void, String> {

    String path , user_id;
    uploadAva uploadAva;

    public ApiUploadAva(uploadAva uploadAva , String path , String user_id){
        this.path = path;
        this.user_id = user_id;
        this.uploadAva = uploadAva;
    }

    @NotNull OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build();



    @Override
    public String doInBackground(Void... voids) {

        File file = new File(path);
        String content_type = getType(file.getPath());
        String file_path = file.getAbsolutePath();

        RequestBody file_body = RequestBody.create(MediaType.parse(content_type) , file);
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("upload_ava" , user_id + ".jpg" , file_body)
                .setType(MultipartBody.FORM)
                .build();

        Request request = new Request.Builder()
                .url("http://"+ global.ip_address +"/fashi/uploadAva.php")
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        uploadAva.finishUploadAva();
    }
}
