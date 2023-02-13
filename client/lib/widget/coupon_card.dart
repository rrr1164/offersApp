import 'package:flutter/material.dart';
import 'package:omnia_client/pojo/utils.dart';

import '../models/coupon.dart';

class CouponCard extends StatelessWidget {
  final Coupon _coupon;

  CouponCard(this._coupon);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
        child: Card(
            child: Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Image.network(
            "${_coupon.pictureUrl}",
            height: 100,
            width: 100,
          ),
          Flexible(
            child: Column(
              children: [
                Padding(
                    padding: const EdgeInsets.only(right: 20, bottom: 20.0),
                    child: Align(
                        alignment: Alignment.topRight,
                        child: Text(
                          "${_coupon.title}",
                          style: TextStyle(
                              fontSize: 24, fontWeight: FontWeight.bold),
                        ))),
                Padding(
                    padding: const EdgeInsets.only(right: 20, bottom: 10.0),
                    child: Align(
                        alignment: Alignment.center,
                        child: Text(
                          "${translate_category(_coupon.category)}",
                          style: TextStyle(fontSize: 18),
                        )))
              ],
            ),
          ),
        ],
      ),
    )));
  }
}
