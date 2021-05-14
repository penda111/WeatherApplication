package hk.edu.ouhk.weatherapplication.ui.LocalForecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hk.edu.ouhk.weatherapplication.R;
import hk.edu.ouhk.weatherapplication.ui.gallery.GalleryViewModel;

import hk.edu.ouhk.weatherapplication.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import hk.edu.ouhk.weatherapplication.R;

public class LocalForecastFragment extends Fragment{
    private LocalForecastViewModel lfvm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        lfvm =
                new ViewModelProvider(this).get(LocalForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_local, container, false);
        final TextView textView = root.findViewById(R.id.text_localforecast);
        lfvm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}


