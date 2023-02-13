import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:omnia_client/cubits/screens_cubit/screen_state.dart';

class ScreenCubit extends Cubit<ScreenState>{
  ScreenCubit() : super(ScreenOne());
  int screen = 0;
  void set_screen(int screen){
    this.screen = screen;
    if(screen == 0){
      emit(ScreenOne());
    }
    else{
      emit(ScreenTwo());
    }
  }
}