import 'package:flutter_bloc/flutter_bloc.dart';

import '../../models/coupon.dart';
import '../../pojo/utils.dart';
import 'coupon_state.dart';

class CouponCubit extends Cubit<CouponState> {
  CouponCubit({required this.coupons}) : super(CouponSelectedFilter()) {
    _allcoupons.addAll(coupons);
    print("coupons:  $coupons");
  }

  List<Coupon> coupons = [];
  List<Coupon> _allcoupons = [];

  int selectedFilter = 0;

  void set_selected_filter(int selected_filter) {
    selectedFilter = selected_filter;
    emit(CouponSelectedFilter());
  }

  searchInData(String query, String type) {
    emit(CouponSearchSearching());
    coupons.clear();
    for (var coupon in _allcoupons) {
      if (type == "title") {
        if (selectedFilter == 0) {
          if (coupon.title.toLowerCase().contains(query.toLowerCase())) {
            coupons.add(coupon);
          }
        } else {
          if (coupon.title.toLowerCase().contains(query.toLowerCase()) &&
              translate_category(coupon.category.toLowerCase()) ==
                  categories[selectedFilter].toLowerCase()) {
            coupons.add(coupon);
          }
        }
      } else if (type == "category") {
        if (selectedFilter > 0) {
          String couponCategory =
              translate_category(coupon.category.toLowerCase());
          if (couponCategory == query.toLowerCase()) {
            coupons.add(coupon);
          }
        } else {
          coupons.add(coupon);
        }
      }
    }
    emit(CouponSearchSuccess());
  }
}
