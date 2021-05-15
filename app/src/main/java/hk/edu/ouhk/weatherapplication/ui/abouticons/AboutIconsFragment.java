package hk.edu.ouhk.weatherapplication.ui.abouticons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import hk.edu.ouhk.weatherapplication.R;

public class AboutIconsFragment extends Fragment{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_abouticons, container, false);


        return root;
    }
}
