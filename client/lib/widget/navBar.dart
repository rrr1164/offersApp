import 'dart:io';

import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:url_launcher/url_launcher.dart';

class NavBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        children: [
          ListTile(
            leading: const Icon(Icons.favorite),
            title: const Text('قيم التطبيق'),
            onTap: () => launchUrl(
              Uri.parse(
                Platform.isAndroid
                    ? "https://play.google.com/store/apps/details?id=main.ClicFlyer"
                    : "https://play.google.com/store/apps/details?id=main.ClicFlyer",
              ),
              mode: LaunchMode.externalApplication,
            ),
          ),
          ListTile(
            leading: const Icon(FontAwesomeIcons.facebook),
            title: const Text('فيسبوك'),
            onTap: () => launchUrl(
              Uri.parse("https://www.facebook.com/omnia.mghd"),
              mode: LaunchMode.externalApplication,
            ),
          ),
          ListTile(
            leading: const Icon(FontAwesomeIcons.instagram),
            title: const Text('انستجرام'),
            onTap: () => launchUrl(
              Uri.parse("https://www.instagram.com/omnia.mgahed/"),
              mode: LaunchMode.externalApplication,
            ),
          ),
          ListTile(
            leading: const Icon(FontAwesomeIcons.whatsapp),
            title: const Text('خدمة العملاء'),
          ),
        ],
      ),
    );
  }
}
