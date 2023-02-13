package com.yousef_shora.omniaoffers.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouponDocument implements Serializable {
    public List<Coupon> coupons_array ;
    public CouponDocument(){
        coupons_array = new ArrayList<>();
    }
}
