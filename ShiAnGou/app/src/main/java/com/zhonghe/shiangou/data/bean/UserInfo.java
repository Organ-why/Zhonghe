package com.zhonghe.shiangou.data.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zhonghe.shiangou.data.db.ColumnHelper;

/**
 * Created by a on 2017/8/18.
 */
@DatabaseTable(tableName = "userinfo")
public class UserInfo extends BaseBean {
    public UserInfo() {
    }

    /**
     * user_id : 21
     * email : 0
     * user_name : yang
     * password : 123
     * user_pic : 0
     * question : 0
     * answer : 0
     * sex : 0
     * birthday : 0000-00-00
     * user_money : 0.00
     * frozen_money : 0.00
     * pay_points : 0
     * rank_points : 0
     * address_id : 0
     * reg_time : 0
     * last_login : 0
     * last_time : 0000-00-00 00:00:00
     * last_ip : 0
     * visit_count : 0
     * user_rank : 0
     * is_special : 0
     * ec_salt : 0
     * salt : 0
     * parent_id : 0
     * flag : 0
     * alias : 0
     * msn : 0
     * qq : 0
     * office_phone : 0
     * home_phone : 0
     * mobile_phone : 0
     * is_validated : 0
     * credit_line : 0.00
     * passwd_question : null
     * passwd_answer : null
     * agree : 0
     */
    @DatabaseField(id = true, columnName = ColumnHelper.UserColumns.USERID)
    private String user_id;
    @DatabaseField(columnName = ColumnHelper.UserColumns.PHONE)
    private String phone;
    private String email;
    @DatabaseField(columnName = ColumnHelper.UserColumns.USERNAME)
    private String user_name;
    private String password;
    @DatabaseField(columnName = ColumnHelper.UserColumns.HEADER)
    private String user_pic;
    private String question;
    private String answer;
    private String sex;
    private String birthday;
    private String user_money;
    private String frozen_money;
    @DatabaseField(columnName = ColumnHelper.UserColumns.POINT)
    private String pay_points;
    private String rank_points;
    private String address_id;
    private String reg_time;
    private String last_login;
    private String last_time;
    private String last_ip;
    private String visit_count;
    private String user_rank;
    private String is_special;
    private String ec_salt;
    private String salt;
    private String parent_id;
    private String flag;
    private String alias;
    private String msn;
    private String qq;
    private String office_phone;
    private String home_phone;
    private String mobile_phone;
    private String is_validated;
    private String credit_line;
    private Object passwd_question;
    private Object passwd_answer;
    private String agree;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getPay_points() {
        return pay_points;
    }

    public void setPay_points(String pay_points) {
        this.pay_points = pay_points;
    }

    public String getRank_points() {
        return rank_points;
    }

    public void setRank_points(String rank_points) {
        this.rank_points = rank_points;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public String getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getIs_special() {
        return is_special;
    }

    public void setIs_special(String is_special) {
        this.is_special = is_special;
    }

    public String getEc_salt() {
        return ec_salt;
    }

    public void setEc_salt(String ec_salt) {
        this.ec_salt = ec_salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getIs_validated() {
        return is_validated;
    }

    public void setIs_validated(String is_validated) {
        this.is_validated = is_validated;
    }

    public String getCredit_line() {
        return credit_line;
    }

    public void setCredit_line(String credit_line) {
        this.credit_line = credit_line;
    }

    public Object getPasswd_question() {
        return passwd_question;
    }

    public void setPasswd_question(Object passwd_question) {
        this.passwd_question = passwd_question;
    }

    public Object getPasswd_answer() {
        return passwd_answer;
    }

    public void setPasswd_answer(Object passwd_answer) {
        this.passwd_answer = passwd_answer;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }
}
