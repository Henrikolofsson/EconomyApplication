package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
            String userName = etUser.getText().toString();
            controller.logIn(userName);
        }
    }

}
