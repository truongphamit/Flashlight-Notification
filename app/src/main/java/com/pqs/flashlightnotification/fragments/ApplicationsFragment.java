package com.pqs.flashlightnotification.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pqs.flashlightnotification.R;
import com.pqs.flashlightnotification.adapters.ApplicationsAdapter;
import com.pqs.flashlightnotification.models.App;
import com.pqs.flashlightnotification.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private List<App> apps;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                List<App> checkApps = new ArrayList<>();
                for (App app: apps) {
                    if (app.isCheck()) checkApps.add(app);
                }
                try {
                    Utils.writeToJson(checkApps, Utils.getFilePath(getActivity()));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                getActivity().onBackPressed();
                break;
        }
        return true;

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
        ab.setTitle(R.string.applications);

        rv_apps.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            apps = Utils.getInstalledApp(getActivity());
            ApplicationsAdapter adapter = new ApplicationsAdapter(getActivity(), apps);
            rv_apps.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
