package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/8/23
 * desc: 评论
 */

public class GoodsPingBean extends BaseBean {
    /**
     * comment_id : 7
     * comment_type : 0
     * id_value : 0
     * email :
     * user_name : 2
     * content : 1111111
     * comment_rank : 55
     * add_time : 1503242513
     * ip_address :
     * status : 0
     * parent_id : 0
     * user_id : 2
     * img : ./Uploads/1503242513877553.jpg
     * goods_id : 115
     */

    private String comment_id;
    private String comment_type;
    private String id_value;
    private String email;
    private String user_name;
    private String nick_name;
    private String content;
    private String comment_rank;
    private String add_time;
    private String ip_address;
    private String status;
    private String parent_id;
    private String user_id;
    private String img;
    private String goods_id;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getId_value() {
        return id_value;
    }

    public void setId_value(String id_value) {
        this.id_value = id_value;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_rank() {
        return comment_rank;
    }

    public void setComment_rank(String comment_rank) {
        this.comment_rank = comment_rank;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
