import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_linkify/flutter_linkify.dart';
import 'package:url_launcher/url_launcher.dart';

import 'filter.dart';

var categories = [
  'الكل',
  'الملابس',
  'المكياج',
  'المطاعم',
  'المقاضي',
  'التوصيل',
  'الخدمات',
  'الطيران',
];

final List<String> icons = ['📜', '👗', '💄', '🍔', '🛒', '🚕', '🛎️', '🛫'];
final List<Filter> filters = [
  new Filter(icon: icons[0], filter: categories[0]),
  new Filter(icon: icons[1], filter: categories[1]),
  new Filter(icon: icons[2], filter: categories[2]),
  new Filter(icon: icons[3], filter: categories[3]),
  new Filter(icon: icons[4], filter: categories[4]),
  new Filter(icon: icons[5], filter: categories[5]),
  new Filter(icon: icons[6], filter: categories[6]),
  new Filter(icon: icons[7], filter: categories[7])
];
Color theme_color = const Color(0xFFf9bc30);

showAlertDialog(BuildContext context, String description, bool coupon) {
  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
      title: Directionality(textDirection:TextDirection.rtl,child: Text("الوصف")),
      content: SingleChildScrollView(
          child: Directionality(
        textDirection: TextDirection.rtl,
        child: coupon == true
            ? Linkify(
                text: description,
                onOpen: (link) async {
                  if (await canLaunchUrl(Uri.parse(link.url))) {
                    await launchUrl(Uri.parse(link.url));
                  } else {
                    throw 'Could not launch $link';
                  }
                },
              )
            : SelectableLinkify(
                text: description,
                onOpen: (link) async {
                  if (await canLaunchUrl(Uri.parse(link.url))) {
                    await launchUrl(Uri.parse(link.url));
                  } else {
                    throw 'Could not launch $link';
                  }
                },
              ),
      )));

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}
translate_category(String category){
  if(category.toLowerCase() == "All".toLowerCase()){
    return "الكل";
  }
  if(category.toLowerCase() == "Fashion".toLowerCase()){
    return "الملابس";
  }
  else if(category.toLowerCase() == "services".toLowerCase()){
    return "الخدمات";
  }
  else if(category.toLowerCase() == "cab".toLowerCase()){
    return "التوصيل";
  }
  else if(category.toLowerCase() == "Restaurant".toLowerCase()){
    return "المطاعم";
  }
  else if(category.toLowerCase() == "Groceries".toLowerCase()){
    return "المقاضي";
  }
  else if(category.toLowerCase() == "Flight".toLowerCase()){
    return "الطيران";
  }
  else if(category.toLowerCase() == "Beauty".toLowerCase()){
    return "المكياج";
  }

}
subscribe_to_notifications() async {
  FirebaseMessaging messaging = FirebaseMessaging.instance;
  await messaging.subscribeToTopic("all");

  NotificationSettings settings = await messaging.requestPermission(
    alert: true,
    announcement: false,
    badge: true,
    carPlay: false,
    criticalAlert: false,
    provisional: false,
    sound: true,
  );
  print('User granted permission: ${settings.authorizationStatus}');
}

