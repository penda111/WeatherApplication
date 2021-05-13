package hk.edu.ouhk.weatherapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static View root = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.temp_high);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        setUserVisibleHint(false);
        setUserVisibleHint(true);

        //ConstraintLayout home = root.findViewById(R.id.fragment_home);
        //home.setBackgroundResource(R.drawable.bg_1);

        return root;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Code executes EVERY TIME user views the fragment
        ((MainActivity)getActivity()).animateSun();
    }

}