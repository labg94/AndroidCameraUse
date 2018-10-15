package com.example.luisa.camerause;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 1;
    private Button botonCamara;
    private ImageView imgCamara;

    private static final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int leer = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(leer == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, PERMISOS,REQUEST_CODE);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        botonCamara = findViewById(R.id.btn_camera);
        imgCamara = findViewById(R.id.img_camera);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            botonCamara.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }else{
            botonCamara.setEnabled(true);
        }



        PackageManager packageManager= getPackageManager();

        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){

            botonCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    Log.w("botonCamara", "Ya paso el intent");

                    File imagenFolder = new File(Environment.getExternalStorageDirectory(),"CamaraFolder");

                    Log.w("botonCamara", "Ya paso el imagenFolder");

                    imagenFolder.mkdirs();
                    Log.w("botonCamara", "Ya paso mkdris");

                    File imagen = new File(imagenFolder,"foto.jpg");
                    Log.w("botonCamara", "Ya paso la imagen");

                    Uri uriImagen = Uri.fromFile(imagen);
                    Log.w("botonCamara", "Ya paso el URI");


                    camerIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriImagen);
                    Log.w("botonCamara", "Ya paso el Extra");
                    if(camerIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(camerIntent, REQUEST_CODE);
                        Log.w("botonCamara", "Ya paso el activity start");
                    }

                }
            });
        }else{
            Toast.makeText(this,"Error de camara",Toast.LENGTH_SHORT);
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this,"Se ha guardado la imagen:\n"+Environment.getExternalStorageDirectory()+"/CamaraFolder/foto.jpg",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"No se guardo correctamente",Toast.LENGTH_LONG).show();
        }

    }
}
