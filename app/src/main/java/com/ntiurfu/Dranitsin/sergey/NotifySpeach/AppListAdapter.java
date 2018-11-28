package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    public List<ExtendAppInfo> mList;
    public Context context;
    public LayoutInflater layoutInflater;
    public PackageManager packageManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView mTextView;
        public CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.imageView);
            mTextView = v.findViewById(R.id.textView);
            this.mCheckBox = v.findViewById(R.id.checkBox);
        }
    }

    List<ExtendAppInfo> sortList(List<ExtendAppInfo> list){
        Collections.sort(list, new Comparator<ExtendAppInfo>() {
            @Override
            public int compare(ExtendAppInfo o1, ExtendAppInfo o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        return list;
    }

    public AppListAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.context = context;
    }

    public void updateData(List<ExtendAppInfo> mList)
    {
        sortList(mList);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Apps",0);
        Set<String> app_set  = sharedPreferences.getStringSet("app",new HashSet<String>());

        for (ExtendAppInfo extendAppInfo : mList) {
            if (app_set.contains(extendAppInfo.package_name)) {

                extendAppInfo.is_checked = true;
            }
        }


        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<ExtendAppInfo> getData()
    {
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ExtendAppInfo app = mList.get(position);

        holder.imageView.setImageDrawable(app.packege_icon);
        holder.mTextView.setText(app.name);
        holder.mCheckBox.setChecked(app.is_checked);

        holder.mCheckBox.setOnCheckedChangeListener(null);

        final CompoundButton.OnCheckedChangeListener myCheck = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(holder.getAdapterPosition()).is_checked = isChecked;
                SharedPreferences sharedPreferences = context.getSharedPreferences("Apps",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Set<String> set = new HashSet<String>();

                for (ExtendAppInfo extendAppInfo : mList) {
                    if(extendAppInfo.is_checked){
                        set.add(extendAppInfo.package_name);
                    }
                }
                editor.putStringSet("app",set);
                editor.apply();
            }
        };

        holder.mCheckBox.setOnCheckedChangeListener(myCheck);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
