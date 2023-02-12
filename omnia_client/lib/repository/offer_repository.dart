import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:get/get.dart';

import '../models/Offer.dart';
import '../pojo/constants.dart';

class OfferRepository extends GetxController {
  static OfferRepository get instance => Get.find();
  final _db = FirebaseFirestore.instance;
  List<Offer> _offers = [];



  Future<void> getOffersFromFirebase() async {
    _offers.clear();
    CollectionReference offers = _db.collection("offers");
    DocumentSnapshot  snapshot = await offers.doc("offers_array").get();
    var data = snapshot.data() as Map;
    var offersData = data['offers_array'] as List<dynamic>;
    offersData.forEach((offerData) {
      Offer offer = Offer.fromJson(offerData);
      _offers.add(offer);

    });
  }
  List<Offer> getOffers() {
    return _offers;
  }
}
