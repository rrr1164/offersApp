import 'package:flutter/material.dart';

import '../models/Offer.dart';
import '../pojo/utils.dart';

class OfferCard extends StatelessWidget {
  final Offer _offer;

  OfferCard(this._offer);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Card(

        child: Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: [
      Image.network(
        "${_offer.pictureUrl}",
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
                      "${_offer.title}",
                      style: TextStyle(
                          fontSize: 24, fontWeight: FontWeight.bold),
                    ))),
            Padding(
                padding: const EdgeInsets.only(right: 20, bottom: 10.0),
                child: Align(
                    alignment: Alignment.center,
                    child: Text(
                      "${translate_category(_offer.category)}",
                      style: TextStyle(fontSize: 18),
                    )))
          ],
        ),
      ),
    ],
      ),
    ));
  }
}
