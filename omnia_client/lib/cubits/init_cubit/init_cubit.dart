
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../models/Offer.dart';
import '../../models/coupon.dart';
import '../../models/coupons_manager.dart';
import '../../models/offers_manager.dart';
import '../../pojo/utils.dart';
import '../../screens/couponsApp.dart';
import '../../screens/offersApp.dart';
import 'init_state.dart';

class InitCubit extends Cubit<InitiationState>{
  InitCubit() : super(InitiationLoading()) {
    init_app();
  }
  List<Offer> _offers = [];
  List<Coupon> _coupons = [];
  List screens = [];

  Future<void> init_app() async {
    emit(InitiationLoading());

    print("initiating app");

    try{
      WidgetsFlutterBinding.ensureInitialized();
      await Firebase.initializeApp();

      await subscribe_to_notifications();
      FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);
      OffersManager offersManager = OffersManager();
      CouponsManager couponsManager = CouponsManager();

      await offersManager.getOffersFromFirebase();
      await couponsManager.getCouponsFromFirebase();

      _offers = offersManager.getOffers();
      _coupons = couponsManager.getCoupons();

      screens = [OffersApp(_offers), CouponsApp(_coupons)];
      emit(InitiationSuccess());
    }
    catch (e){
        print("exception: $e");
        emit(InitiationError());
    }
  }

  @pragma('vm:entry-point')
  Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {

    print("Handling a background message: ${message.messageId}");
  }
}