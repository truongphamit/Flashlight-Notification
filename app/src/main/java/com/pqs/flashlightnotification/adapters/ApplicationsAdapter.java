package com.pqs.flashlightnotification.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pqs.flashlightnotification.R;
import com.pqs.flashlightnotification.utils.Utils;

import java.util.List;

/**
 * Created by truongpq on 5/3/17.
 */

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ViewHolder>{
    private Context context;
    private List<ApplicationInfo> applicationInfos;

    public ApplicationsAdapter(Context context, List<ApplicationInfo> applicationInfos) {
        this.context = context;
        this.applicationInfos = applicationInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_app, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApplicationInfo applicationInfo = applicationInfos.get(position);
        holder.app_icon.setImageDrawable(Utils.getAppIcon(context, applicationInfo));
        holder.app_name.setText(Utils.getAppName(context, applicationInfo));
    }

    @Override
    public int getItemCount() {
        return applicationInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView app_icon;
        public TextView app_name;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
