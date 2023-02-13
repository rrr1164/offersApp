import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_linkify/flutter_linkify.dart';
import 'package:url_launcher/url_launcher.dart';

import 'filter.dart';

ButtonStyle buttonStyle = ElevatedButton.styleFrom(
  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15.0)),
  padding: const EdgeInsets.symmetric(horizontal: 100.0, vertical: 25.0),
);
const success_snackBar = SnackBar(
  content: Text('successfully added data'),
);
const error_snackBar = SnackBar(
  content: Text('error adding data'),
);

String LIMITED_OFFER_IMAGE =
    "https://static.vecteezy.com/system/resources/previews/005/020/297/original/limited-time-offer-design-in-red-and-black-with-stop-watch-free-vector.jpg"; //for the variables which will not change,
