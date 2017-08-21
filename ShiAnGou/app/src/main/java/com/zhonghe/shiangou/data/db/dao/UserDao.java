package com.zhonghe.shiangou.data.db.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.data.db.ColumnHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by a on 2017/8/18.
 */

public class UserDao extends BaseDao<UserInfo> {
    public UserDao(ConnectionSource connectionSource, Class<UserInfo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    /**
     * 添加或更新用户
     *
     * @param user
     */
    public void addUser(UserInfo user) {
        if (user == null) return;
        try {
            createOrUpdate(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户id获取当前用户信息
     *
     * @param uid
     * @return
     */
    public UserInfo getUser(String uid) {
        if (UtilString.isBlank(uid)) return null;
        List<UserInfo> infos = null;
        try {
            infos = queryForEq(ColumnHelper.UserColumns.USERID, uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return UtilList.isNotEmpty(infos) ? infos.get(0) : null;
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    public List<UserInfo> getUsers() {
        List<UserInfo> users = null;
        try {
            users = queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
