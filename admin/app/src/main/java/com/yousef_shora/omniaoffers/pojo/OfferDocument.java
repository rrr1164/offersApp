package com.yousef_shora.omniaoffers.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OfferDocument implements Serializable {
    public List<Offer> offers_array ;
    public OfferDocument(){
        offers_array = new ArrayList<>();
    }
}
