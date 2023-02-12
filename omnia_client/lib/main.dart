import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:omnia_client/cubits/init_cubit/init_state.dart';
import 'package:omnia_client/cubits/screens_cubit/screen_cubit.dart';

import 'cubits/init_cubit/init_cubit.dart';
import 'cubits/screens_cubit/screen_state.dart';
import 'widget/navBar.dart';

void main() async {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    home: Scaffold(backgroundColor: Colors.white, body: MainApp()),
    theme: ThemeData(
        colorScheme: ColorScheme.fromSwatch().copyWith(
      primary: const Color(0xFFf9bc30),
      secondary: const Color(0xFFFFC107),
    )),
  ));
}

class MainApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) => InitCubit(),
        child: BlocBuilder<InitCubit, InitiationState>(
            builder: (BuildContext context, state) {
          if (state is InitiationLoading) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is InitiationError) {
            return const Center(child: Text("an Error occured initiation app"));
          } else {
            return MainBody();
          }
        }));
  }
}

class MainBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
          colorScheme: ColorScheme.fromSwatch().copyWith(
        primary: const Color(0xFFf9bc30),
        secondary: const Color(0xFFFFC107),
      )),
      debugShowCheckedModeBanner: false,
      home: BlocProvider(
        create: (context) => ScreenCubit(),
        child: BlocBuilder<ScreenCubit, ScreenState>(
          builder: (context, state) {
            return Directionality(
              textDirection: TextDirection.rtl,
              child: Scaffold(
                drawer: NavBar(),
                body: BlocProvider.of<InitCubit>(context)
                    .screens[BlocProvider.of<ScreenCubit>(context).screen],
                bottomNavigationBar: BottomNavigationBar(
                  currentIndex: BlocProvider.of<ScreenCubit>(context).screen,
                  type: BottomNavigationBarType.fixed,
                  selectedItemColor: Colors.blueAccent,
                  onTap: (index) =>
                      BlocProvider.of<ScreenCubit>(context).set_screen(index),
                  items: const [
                    BottomNavigationBarItem(
                        icon: Icon(Icons.local_offer), label: 'العروض'),
                    BottomNavigationBarItem(
                        icon: Icon(Icons.shopping_basket), label: 'الكوبونات'),
                  ],
                ),
                appBar: AppBar(
                  title: const Text(
                    "الحق عروض السعوديه",
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}
