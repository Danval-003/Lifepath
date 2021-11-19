package com.example.lifepath;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

public class Menu extends AppCompatActivity {
    //Instanciamos la variable
    MeowBottomNavigation bottomNavigation;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Asignamos la variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        
        //Añadimos los items al menú
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_food));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_movemente));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Instanciamos los fragmentos.
                Fragment fragment = null;
                Intent i = getIntent();
                //Verificamos las condiciones
                switch (item.getId()){
                    case 1:
                        //Cuando el id es 1
                        //Instanciamos el fragmento que muestra el perfil
                        fragment = new ProfileFragment(i.getStringExtra("nombre"),i.getStringExtra("edad") );
                        break;
                    case 2:
                        //Cuando el id es 2
                        //Instanciamos el fragmento que muestra el apartado de nutricion
                        fragment = new FoodFragment();
                        break;
                    case 3:
                        //Cuando el id es 3
                        //Instanciamos el fragmento que muestra el apartado de ejercicios
                        fragment = new MovementFragment();
                        break;
                }
                //Cargamos los fragmentos
                loadFragment(fragment);
            }
        });

        //Establecemos un contador de notificaciones
        bottomNavigation.setCount(1, "10");
        //Establecemos la instancia seleccionada del fragmento perfiles
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Mostrar la tostada
                /***Toast.makeText(getApplicationContext()
                 , "Seleccionaste " + item.getId()
                 ,Toast.LENGTH_SHORT).show();*/
            }
        });

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Mostramos la tostada
                /***Toast.makeText(getApplicationContext()
                 , "Reseleccionaste " + item.getId()
                 ,Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        //Reemplazamos el fragmento
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    FoodFragment f = new FoodFragment();
                    f.getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     private void getTextFromImage(Bitmap bitmap){
     TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
     if (!recognizer.isOperational()){
     Toast.makeText(Menu.this,"Un error ocurrió!", Toast.LENGTH_SHORT).show();
     }
     else {
     Frame frame = new Frame.Builder().setBitmap(bitmap).build();
     SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
     StringBuilder stringBuilder = new StringBuilder();
     for (int i = 0; i < textBlockSparseArray.size(); i++) {
     TextBlock textBlock = textBlockSparseArray.valueAt(i);
     stringBuilder.append(textBlock.getValue());
     stringBuilder.append("\n");
     }
     }
     visualizadorTexto.setText(stringBuilder.toString());
     botonCapturar.setText("Intentar nuevamente");
     butonCopiar.setVisibility(View.VISIBLE);
     }
     }*/


    public void copiadoalPortapapeles(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Datos copiados!", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(Menu.this,"Copiado al portapapeles!",Toast.LENGTH_SHORT).show();
    }
}