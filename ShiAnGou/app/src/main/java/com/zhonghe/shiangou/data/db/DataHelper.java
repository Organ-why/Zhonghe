package com.zhonghe.shiangou.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.system.constant.CstProject;

import java.sql.SQLException;

/**
 * Created by a on 2017/8/18.
 */

public class DataHelper extends OrmLiteSqliteOpenHelper {
    public DataHelper(Context context) {
        super(context, CstProject.DB_NAME, null, CstProject.DB_VERSION);
//        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.e(CstProject.PROJECT, "create user table");
            TableUtils.createTable(connectionSource, UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新数据库
        if (oldVersion != newVersion) {

        }
    }
}
