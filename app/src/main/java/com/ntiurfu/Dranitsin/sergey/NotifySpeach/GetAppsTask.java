package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GetAppsTask extends AsyncTask<Void, Void, Void> {

    public Context context;
    public PackageManager manager;
    public ArrayList<ExtendAppInfo> packages;
    public RecyclerView mListView;

    public MainActivity activity;

    public GetAppsTask(Context context, RecyclerView mListView, MainActivity activity) {
        this.context = context;
        this.manager = context.getPackageManager();
        this.mListView = mListView;
       this.activity = activity;
    }


    protected Void doInBackground(Void... params) {
        activity.progressBar.setVisibility(View.VISIBLE);
        //Log.d("GetAppTast", "Start");

        //Log.d("apps", manager.toString());
        List<ApplicationInfo> packs = manager.getInstalledApplications(PackageManager.GET_META_DATA);
        packages = new ArrayList<>();
        //Log.d("apps", packs.size()+"");
        for (int i=0;i<packs.size();i++) {
            ApplicationInfo app = packs.get(i);

            packages.add(new ExtendAppInfo(app.loadLabel(manager).toString(),app.packageName, app.loadIcon(manager)));
        }

        return null;
    }


    protected void onPostExecute(Void result) {
        //Log.d("onPostExec", String.valueOf(packages.isEmpty()));
        activity.progressBar.setVisibility(View.INVISIBLE);

        AppListAdapter appListAdapter = (AppListAdapter)mListView.getAdapter();
        appListAdapter.updateData(packages);
    }


}
