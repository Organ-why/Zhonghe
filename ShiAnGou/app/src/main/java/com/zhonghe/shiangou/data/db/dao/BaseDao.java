package com.zhonghe.shiangou.data.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by a on 2017/8/18.
 */

public class BaseDao<T> extends BaseDaoImpl<T, Integer> {
    protected BaseDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
