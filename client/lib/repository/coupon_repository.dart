import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:get/get.dart';

import '../models/coupon.dart';
import '../pojo/constants.dart';

class CouponRepository extends GetxController {
  static CouponRepository get instance => Get.find();
  final _db = FirebaseFirestore.instance;
  List<Coupon> _coupons = [];

  Future<void> getCouponsFromFirebase() async {
    _coupons.clear();
    CollectionReference offers = _db.collection("offers");
    DocumentSnapshot snapshot = await offers.doc("coupons_array").get();
    var data = snapshot.data() as Map;
    var couponsData = data['coupons_array'] as List<dynamic>;
    couponsData.forEach((couponData) {
      Coupon coupon = Coupon.fromJson(couponData);
      _coupons.add(coupon);
    });
  }

  List<Coupon> getCoupons() {
    return _coupons;
  }
}
