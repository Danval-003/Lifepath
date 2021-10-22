package com.example.lifepath;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Menu extends AppCompatActivity {
    //Instanciamos la variable
    MeowBottomNavigation bottomNavigation;

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
                //Verificamos las condiciones
                switch (item.getId()){
                    case 1:
                        //Cuando el id es 1
                        //Instanciamos el fragmento que muestra el perfil
                        fragment = new ProfileFragment();
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
}