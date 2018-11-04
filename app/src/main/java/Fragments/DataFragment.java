package Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private String activeFragment;
    private Controller controller;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        return view;
    }

    public void setActiveFragment(String tag){
        activeFragment = tag;
    }

    public String getActiveFragment(){
        return  activeFragment;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
