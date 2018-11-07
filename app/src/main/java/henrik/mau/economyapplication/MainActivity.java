package henrik.mau.economyapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Controller controller;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();

    }

    private void initializeSystem() {
        fm = getSupportFragmentManager();
        controller = new Controller(this);
    }

    public void onBackPressed(){
        if(controller.onBackPressed()){
            super.onBackPressed();
        }
    }

    public void setFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(fragment, tag);
        fragmentTransaction.commit();
    }

    public Fragment getFragment(String tag){
        return fm.findFragmentByTag(tag);
    }

    public void onResume(){
        super.onResume();
        controller.onResume();
    }
}
