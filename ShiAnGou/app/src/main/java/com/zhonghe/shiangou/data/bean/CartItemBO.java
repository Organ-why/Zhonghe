package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 */
public class CartItemBO extends BaseBean {

    private static final long serialVersionUID = 1L;
    boolean isCheck;
    private Integer id;//购物车ID
    private Integer userId;//用户(买家ID)
    private Integer productId;//商品ID
    private Integer skuId;//skuID
    private Integer shopId;//店铺ID
    private Integer amount;//商品数量
    private Integer status;//状态 default = 0, 主动删除 = 1, 下单成功 = 2, 加入收藏夹 = 3
    private Long createdAt;//创建时间
    private Long updatedAt;//更新时间
//    private Shop shop;//店铺
//    private Product product;//商品
//    private Sku sku;//商品sku
//    private List<PurchaseActivitys> activitys;//商品满减


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

//    public Shop getShop() {
//        return shop;
//    }
//
//    public void setShop(Shop shop) {
//        this.shop = shop;
//    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Sku getSku() {
//        return sku;
//    }
//
//    public void setSku(Sku sku) {
//        this.sku = sku;
//    }
//
//
//    public List<PurchaseActivitys> getActivitys() {
//        return activitys;
//    }
//
//    public void setActivitys(List<PurchaseActivitys> activitys) {
//        this.activitys = activitys;
//    }
}

