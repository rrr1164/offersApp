import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../cubits/offers_cubit/offer_cubit.dart';
import '../cubits/offers_cubit/offer_state.dart';
import '../models/Offer.dart';
import '../pojo/utils.dart';
import '../widget/offer_card.dart';

class OffersApp extends StatelessWidget {
  final List<Offer> _offers = [];

  OffersApp(List<Offer> offers, {super.key}) {
    _offers.addAll(offers);
  }

  @override
  Widget build(BuildContext context) {
    return _OffersApp(_offers);
  }
}

class _OffersApp extends StatelessWidget {
  List<Offer> copy_offers = [];

  _OffersApp(List<Offer> offers) {
    List<Offer> copy = [...offers];
    copy_offers.addAll(copy);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Directionality(
        textDirection: TextDirection.rtl,
        child: Scaffold(
          resizeToAvoidBottomInset: false,
          body: BlocProvider(
            create: (context) => OfferCubit(offers: copy_offers),
            child:
                BlocBuilder<OfferCubit, OfferState>(builder: (context, state) {
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
                          BlocProvider.of<OfferCubit>(context)
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
                                                BlocProvider.of<OfferCubit>(
                                                                context)
                                                            .selectedFilter ==
                                                        index
                                                    ? Colors.blue
                                                    : Colors.white)),
                                    onPressed: () {
                                      BlocProvider.of<OfferCubit>(context)
                                          .set_selected_filter(index);
                                      print(
                                          "selected filter: ${BlocProvider.of<OfferCubit>(context).selectedFilter}");
                                      BlocProvider.of<OfferCubit>(context)
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
                                                  BlocProvider.of<OfferCubit>(
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
                                  BlocProvider.of<OfferCubit>(context)
                                      .offers[index]
                                      .description,
                                  false),

                              style: ButtonStyle(
                                  backgroundColor:
                                      MaterialStateProperty.all(Colors.black.withOpacity(0.0)),elevation: MaterialStateProperty.all(0.0)),
                              child: OfferCard(
                                  BlocProvider.of<OfferCubit>(context)
                                      .offers[index]));
                        },
                        itemCount:
                            BlocProvider.of<OfferCubit>(context).offers.length,
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
