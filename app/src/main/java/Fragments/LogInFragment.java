package Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {
    private Controller controller;
    private EditText etUser;
    private Button btnLogIn;
    public String name;


    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        initializeComponents(view);
        registerListeners();


        return view;
    }

    private void initializeComponents(View view){
        etUser = (EditText) view.findViewById(R.id.etLogIn);
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
    }

    private void registerListeners(){
        btnLogIn.setOnClickListener(new LogInButtonListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class LogInButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
                controller.setName(etUser.getText().toString());
                name = etUser.getText().toString();
                controller.addButtonsFragment();
        }
    }

    public String getName(){
        return name;
    }

    @Override
    public void onResume() {
        super.onResume();
        readName();
        etUser.setText(name);
        Log.d("LOGIN", "onResume: " + name);

    }

    @Override
    public void onPause() {
        super.onPause();
        writeName();


        Log.d("LOGIN", "onPause: " + name);
    }


    public void writeName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("LogInFragment", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", etUser.getText().toString());
        editor.commit();
    }

    public void readName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("LogInFragment", AppCompatActivity.MODE_PRIVATE);
        name = sharedPref.getString("name","");
        System.out.print(name);
    }

}
