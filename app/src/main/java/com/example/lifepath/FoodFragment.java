package com.example.lifepath;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button botonCapturar, botoncopiar;
    TextView visualizadorTexto;
    private static final int REQUEST_CAMERA_CODE = 100;
    Bitmap bitmap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            botonCapturar = getView().findViewById(R.id.boton_captura);
            botoncopiar = getView().findViewById(R.id.boton_copiar);
            visualizadorTexto = getView().findViewById(R.id.text_data);
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)){
                requestPermissions(new String[]{
                    Manifest.permission.CAMERA
                }, REQUEST_CAMERA_CODE);
            }
            botonCapturar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getActivity());
                }
            });
            botoncopiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textoscanneado = visualizadorTexto.getText().toString();
                    copiarAlPortapapeles(textoscanneado);
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == Menu.RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), resultUri);
                    obtenerTextoImagen(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void  obtenerTextoImagen(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(getContext()).build();
        if (!recognizer.isOperational()){
            Toast.makeText(getContext(), "Un error a ocurrido !!!", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<textBlockSparseArray.size();i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            visualizadorTexto.setText(stringBuilder.toString());
            botonCapturar.setText("Retomar captura");
            botoncopiar.setVisibility(View.VISIBLE);
        }
    }

    private void copiarAlPortapapeles(String texto){
        ClipboardManager portapapeles = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Datos copiados", texto);
        portapapeles.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copiado al portapapeles", Toast.LENGTH_SHORT).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);


    }
}