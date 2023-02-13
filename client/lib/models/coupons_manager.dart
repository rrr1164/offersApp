import 'dart:io';

import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

import '../repository/coupon_repository.dart';
import '../repository/offer_repository.dart';
import 'Offer.dart';
import 'coupon.dart';

class CouponsManager {
  List<Coupon> _coupons = [];

  final couponRepo = Get.put(CouponRepository());

  getCouponsFromFirebase() async {
    await couponRepo.getCouponsFromFirebase().whenComplete(() {
      _coupons = couponRepo.getCoupons();
    });
  }

  getCoupons() {
    List<Coupon> copy = [..._coupons.reversed];
    return copy;
  }
}