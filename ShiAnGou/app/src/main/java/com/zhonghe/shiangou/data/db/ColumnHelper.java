package com.zhonghe.shiangou.data.db;

import android.provider.BaseColumns;

/**
 * Created by a on 2017/8/18.
 */

public class ColumnHelper {
    public static class UserColumns implements BaseColumns {
        //用户ID
        public static final String USERID = "_uid";
        public static final String USERNAME = "_userName";
        public static final String PHONE = "_phone";
        public static final String HEADER = "_header";
        public static final String POINT = "_point";
    }
}
