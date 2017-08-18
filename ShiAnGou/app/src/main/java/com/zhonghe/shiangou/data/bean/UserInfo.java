package com.zhonghe.shiangou.data.bean;

import com.j256.ormlite.field.DatabaseField;
import com.zhonghe.shiangou.data.db.ColumnHelper;

/**
 * Created by a on 2017/8/18.
 */

public class UserInfo extends BaseBean {

    @DatabaseField(id = true, columnName = ColumnHelper.UserColumns.USERID)
    private String id;
    @DatabaseField(columnName = ColumnHelper.UserColumns.USERNAME)
    private String username;
    @DatabaseField(columnName = ColumnHelper.UserColumns.HEADER)
    private String header;
    @DatabaseField(columnName = ColumnHelper.UserColumns.PHONE)
    private String phone;

}
