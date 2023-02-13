import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:omnia_client/cubits/coupons_cubit/coupon_state.dart';

import '../cubits/coupons_cubit/coupon_cubit.dart';
import '../models/coupon.dart';
import '../pojo/utils.dart';
import '../widget/coupon_card.dart';

class CouponsApp extends StatelessWidget {
  final List<Coupon> _coupons = [];

  CouponsApp(List<Coupon> coupons, {super.key}) {
    _coupons.addAll(coupons);
  }

  @override
  Widget build(BuildContext context) {
    return _CouponsApp(_coupons);
  }
}

class _CouponsApp extends StatelessWidget {
  List<Coupon> copy_coupons = [];

  _CouponsApp(List<Coupon> coupons) {
    List<Coupon> copy = [...coupons];
    copy_coupons.addAll(copy);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
          colorScheme: ColorScheme.fromSwatch().copyWith(
        primary: const Color(0xFFf9bc30),
        secondary: const Color(0xFFFFC107),
      )),
      home: Directionality(
        textDirection: TextDirection.rtl,
        child: Scaffold(
          resizeToAvoidBottomInset: false,
          body: BlocProvider(
            create: (context) => CouponCubit(coupons: copy_coupons),
            child: BlocBuilder<CouponCubit, CouponState>(
                builder: (context, state) {
              return SingleChildScrollView(
                physics: const ScrollPhysics(),
                child: Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      TextField(
                        decoration: InputDecoration(
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(20.0)),
                            hintText: "أبحث هنا"),
                        onChanged: (query) {
                          BlocProvider.of<CouponCubit>(context)
                              .searchInData(query, "title");
                        },
                      ),
                      Container(
                          margin: const EdgeInsets.only(top: 20, bottom: 10),
                          height: 91,
                          child: ListView.builder(
                            shrinkWrap: true,
                            itemBuilder: (context, index) {
                              return Container(
                                width: 100,
                                margin: const EdgeInsets.only(left: 20),
                                child: ElevatedButton(
                                    style: ButtonStyle(
                                        backgroundColor:
                                            MaterialStateProperty.all(
                                                BlocProvider.of<CouponCubit>(
                                                                context)
                                                            .selectedFilter ==
                                                        index
                                                    ? Colors.blue
                                                    : Colors.white)),
                                    onPressed: () {
                                      BlocProvider.of<CouponCubit>(context)
                                          .set_selected_filter(index);
                                      print(
                                          "selected filter: ${BlocProvider.of<CouponCubit>(context).selectedFilter}");
                                      BlocProvider.of<CouponCubit>(context)
                                          .searchInData(
                                              categories[index], "category");
                                    },
                                    child: Column(
                                      children: [
                                        const SizedBox(
                                          height: 15,
                                        ),
                                        Text(
                                          filters[index].icon,
                                          style: const TextStyle(fontSize: 25),
                                        ),
                                        const SizedBox(
                                          height: 15,
                                        ),
                                        Text(
                                          filters[index].filter,
                                          style: TextStyle(
                                              fontSize: 13.5,
                                              color:
                                                  BlocProvider.of<CouponCubit>(
                                                                  context)
                                                              .selectedFilter ==
                                                          index
                                                      ? Colors.white
                                                      : Colors.black),
                                        ),
                                      ],
                                    )),
                              );
                            },
                            scrollDirection: Axis.horizontal,
                            itemCount: icons.length,
                          )),
                      ListView.builder(
                        key: UniqueKey(),
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemBuilder: (context, index) {
                          return ElevatedButton(
                              onPressed: () => showAlertDialog(
                                  context,
                                  BlocProvider.of<CouponCubit>(context)
                                      .coupons[index]
                                      .description,
                                  false),
                              style: ButtonStyle(
                                  backgroundColor:
                                  MaterialStateProperty.all(Colors.black.withOpacity(0.0)),elevation: MaterialStateProperty.all(0.0)),
                              child: CouponCard(
                                  BlocProvider.of<CouponCubit>(context)
                                      .coupons[index]));
                        },
                        itemCount: BlocProvider.of<CouponCubit>(context)
                            .coupons
                            .length,
                      ),
                    ],
                  ),
                ),
              );
            }),
          ),
        ),
      ),
    );
  }
}
