import 'package:flutter/material.dart';

class MyTextField extends StatelessWidget {
  String labelText;
  TextEditingController controller;

  MyTextField({required this.labelText, required this.controller});

  @override
  Widget build(BuildContext context) {
    return TextFormField(
        keyboardType: TextInputType.text,
        decoration: InputDecoration(
          border: OutlineInputBorder(),
          labelText: labelText,
          labelStyle: TextStyle(color: Colors.black, fontSize: 15.0),
        ),
        controller: controller,
        validator: (value) {
          if (value == null || value.isEmpty) {
            return 'Please enter $labelText';
          } else if (value.length < 2) {
            return 'Please add values';
          }
          return null;
        },
        autovalidateMode: AutovalidateMode.onUserInteraction);
  }
}
