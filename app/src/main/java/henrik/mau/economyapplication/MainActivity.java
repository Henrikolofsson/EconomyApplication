package henrik.mau.economyapplication;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Controller controller;
    private FragmentManager fm;
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
           //Restore fragment instance
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save fragment instance
        super.onSaveInstanceState(outState);
    }
}
