package com.amlzq.android.content.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by amlzq on 2018/9/11.
 * <p>
 * PackageInfo Subclass
 */

public class PackageInfoSub extends PackageInfo {

    public PackageStats packageStats; // 包统计

    public static class DisplayNameComparator
            implements Comparator<PackageInfo> {
        public DisplayNameComparator(PackageManager pm) {
            mPM = pm;
        }

        public final int compare(PackageInfo aa, PackageInfo ab) {
            CharSequence sa = mPM.getApplicationLabel(aa.applicationInfo);
            if (sa == null) {
                sa = aa.packageName;
            }
            CharSequence sb = mPM.getApplicationLabel(ab.applicationInfo);
            if (sb == null) {
                sb = ab.packageName;
            }

            return sCollator.compare(sa.toString(), sb.toString());
        }

        private final Collator sCollator = Collator.getInstance();
        private PackageManager mPM;
    }

}