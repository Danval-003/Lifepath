package com.example.lifepath;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovementFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button start, stop,reset;
    private boolean isOn=false;
    Thread cronos;
    TextView crono;
    private int mili=0, seg=0, minutos=0;
    private Handler h =new Handler();


    public MovementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovementFragment newInstance(String param1, String param2) {
        MovementFragment fragment = new MovementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View j= inflater.inflate(R.layout.fragment_movement, container, false);
         start=(Button) j.findViewById(R.id.stat);
         stop=(Button) j.findViewById(R.id.ssp);
         reset=(Button) j.findViewById(R.id.reset);
         crono = (TextView) j.findViewById(R.id.numeros);
         start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);
        cronos = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (isOn){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mili++;
                        if (mili==999){
                            seg++;
                            mili=0;
                        }
                        if(seg==59){
                            minutos++;
                            seg=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String m ="", s="", mi="";
                                if(mili<10){
                                    m="00"+mili;
                                }else if (mili<100){
                                    m="0"+mili;
                                }else{ m=""+mili;}

                                if (seg<10){
                                    s="0"+seg;
                                }else {s=""+seg;}
                                if (minutos<10){
                                    mi="0"+minutos;
                                }else {mi=""+minutos;}
                                crono.setText(mi+":"+s+":"+m);
                            }
                        });

                    }
                }
            }
        });
        cronos.start();

        Button s = (Button) j.findViewById(R.id.rut);
        s.setOnClickListener(view -> {
            Intent o= new Intent(getActivity(), Rutinas.class);
            getActivity().startActivity(o);

        });
         return j;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stat:
                isOn=true;
                break;
            case R.id.ssp:
                isOn=false;
                break;
            case R.id.reset:
                isOn=false;
                mili=0;
                seg=0;
                minutos=0;
                crono.setText("00:00:000");
                break;
        }

    }
}