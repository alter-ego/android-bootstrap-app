package solutions.alterego.bootstrap.core;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

}