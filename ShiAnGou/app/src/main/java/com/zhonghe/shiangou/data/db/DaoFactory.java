package com.zhonghe.shiangou.data.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by a on 2017/8/18.
 */

public class DaoFactory {
    private static DaoFactory mInstance;
    private ConnectionSource mSource;

    //用户dao
//    private UserDao mUserDao;
//    //
//    private NativeWorksDao mnativeWorksDao;


    private DaoFactory(Context ctx) {
        this.mSource = OpenHelperManager.getHelper(ctx, DataHelper.class)
                .getConnectionSource();

    }

    /**
     * 清除表中的所有数据
     */
    public void clear() {
//        try {
//            UtilLog.e("drop user table");
//            TableUtils.dropTable(mSource, MemberInfo.class, true);
//            UtilLog.e("create user table");
//            TableUtils.createTable(mSource, MemberInfo.class);UtilLog.e("drop user table");
//            UtilLog.e("drop NativeWorks table");
//            TableUtils.dropTable(mSource, NativeWorksInfo.class, true);
//            UtilLog.e("create NativeWorks table");
//            TableUtils.createTable(mSource, NativeWorksInfo.class);
//        } catch (SQLException e) {
//
//        }
    }

    /**
     * 单例
     *
     * @param ctx
     * @return
     */
    public static synchronized DaoFactory getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DaoFactory(ctx);
        }
        return mInstance;
    }
}
