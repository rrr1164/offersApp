import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:omnia_client/cubits/offers_cubit/offer_state.dart';
import 'package:omnia_client/models/offers_manager.dart';

import '../../models/Offer.dart';
import '../../pojo/utils.dart';

class OfferCubit extends Cubit<OfferState> {

  OfferCubit({required this.offers}) : super(OfferSelectedFilter()) {
    _alloffers.addAll(offers);
    print("offers:  $offers");
  }

  List<Offer> offers = [];
  List<Offer> _alloffers = [];

  int selectedFilter = 0;

  void set_selected_filter(int selected_filter){
    selectedFilter = selected_filter;
    emit(OfferSelectedFilter());
  }

  searchInData(String query, String type) {
    offers.clear();
    for (var offer in _alloffers) {
      if (type == "title") {
        if (selectedFilter == 0) {
          if (offer.title.toLowerCase().contains(query.toLowerCase())) {
            offers.add(offer);
          }
        } else {
          if (offer.title.toLowerCase().contains(query.toLowerCase()) &&
              translate_category(offer.category.toLowerCase()) ==
                  categories[selectedFilter].toLowerCase()) {
            offers.add(offer);
          }
        }
      } else if (type == "category") {
        if (selectedFilter > 0) {
          String couponCategory =
              translate_category(offer.category.toLowerCase());
          if (couponCategory == query.toLowerCase()) {
            offers.add(offer);
          }
        } else {
          offers.add(offer);
        }
      }
    }
    emit(OfferSearchSuccess());
  }
}
