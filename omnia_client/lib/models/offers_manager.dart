
import 'dart:io';

import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

import '../repository/offer_repository.dart';
import 'Offer.dart';

class OffersManager {
  List<Offer> _offers = [];
  final offerRepo = Get.put(OfferRepository());


   getOffersFromFirebase() async {
    await offerRepo.getOffersFromFirebase().whenComplete(() {
      _offers = offerRepo.getOffers();
    } );
  }
  List<Offer> getOffers(){
    List<Offer> copy = [..._offers.reversed];
    return copy;
  }
}