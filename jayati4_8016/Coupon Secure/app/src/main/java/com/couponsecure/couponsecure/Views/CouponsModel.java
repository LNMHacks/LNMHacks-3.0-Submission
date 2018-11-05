package com.couponsecure.couponsecure.Views;

class CouponsModel {

    String couponId, counponNo, counponBy ;
    Boolean location, expiery;
    String offer;

    public CouponsModel(String couponId, String counponNo, String counponBy, Boolean location, Boolean expiery, String offer) {
        this.couponId = couponId;
        this.counponNo = counponNo;
        this.counponBy = counponBy;
        this.location = location;
        this.expiery = expiery;
        this.offer = offer;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCounponNo() {
        return counponNo;
    }

    public void setCounponNo(String counponNo) {
        this.counponNo = counponNo;
    }

    public String getCounponBy() {
        return counponBy;
    }

    public void setCounponBy(String counponBy) {
        this.counponBy = counponBy;
    }

    public Boolean getLocation() {
        return location;
    }

    public void setLocation(Boolean location) {
        this.location = location;
    }

    public Boolean getExpiery() {
        return expiery;
    }

    public void setExpiery(Boolean expiery) {
        this.expiery = expiery;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
