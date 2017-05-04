package com.pqs.flashlightnotification.fragments;


import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pqs.flashlightnotification.R;
import com.pqs.flashlightnotification.adapters.ApplicationsAdapter;
import com.pqs.flashlightnotification.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationsFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_apps)
    RecyclerView rv_apps;

    public static ApplicationsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ApplicationsFragment fragment = new ApplicationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ApplicationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applications, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        rv_apps.setLayoutManager(new LinearLayoutManager(getActivity()));
        ApplicationsAdapter adapter = new ApplicationsAdapter(getActivity(), Utils.getInstalledApp(getActivity()));
        rv_apps.setAdapter(adapter);
    }
}
