package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.graphics.drawable.Drawable;

/**
 * Created by Sergey on 06.11.2017.
 */

public class ExtendAppInfo {
    boolean is_checked = false;
    String name = "";
    String package_name = "";
    Drawable packege_icon;

    public ExtendAppInfo(String name, String package_name, Drawable packege_icon) {
        this.is_checked = false;
        this.name = name;
        this.package_name = package_name;
        this.packege_icon = packege_icon;

    }

}
