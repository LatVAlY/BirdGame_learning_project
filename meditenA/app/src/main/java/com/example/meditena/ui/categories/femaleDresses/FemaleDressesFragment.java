package com.example.meditena.ui.categories.femaleDresses;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meditena.R;

public class FemaleDressesFragment extends Fragment {

    private FemaleDressesViewModel mViewModel;

    public static FemaleDressesFragment newInstance() {
        return new FemaleDressesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.female_dresses_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FemaleDressesViewModel.class);
        // TODO: Use the ViewModel
    }

}
