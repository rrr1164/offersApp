import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:simple_circular_progress_bar/simple_circular_progress_bar.dart';

class SplashScreen extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SimpleCircularProgressBar(
                progressColors: const [Colors.cyan],
                animationDuration: 15,
              ),
              SizedBox(height: 35,),
              Text("تأكد من اتصالك بالأنترنت",style: TextStyle(fontSize: 40,fontWeight: FontWeight.bold),)
            ],
          ),
        ));
  }

}