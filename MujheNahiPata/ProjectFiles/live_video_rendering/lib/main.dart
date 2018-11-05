import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

void main() => runApp(MyApp());

String url = '...';

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.deepPurple,
      ),
      home: VideoPlayerView(),
    );
  }
}
class VideoPlayerView extends StatefulWidget{
  @override
  State createState() => VideoPlayerViewState();
}

class VideoPlayerViewState extends State<VideoPlayerView>{
  bool view = false;

  @override 
  Widget build(BuildContext context){
    return Scaffold(
      body: Container(child:  WebviewScaffold(
            url: url
          ),
          height: MediaQuery.of(context).size.width * 0.5,
          ),
    );
}
}


  // void connect(InternetAddress clientAddress, int port){
  //   Future.wait([RawDatagramSocket.bind(InternetAddress.ANY_IP_V4, 0)]).then((values){
  //     RawDatagramSocket socket = values[0];
  //     socket.listen((RawSocketEvent e){
  //       switch(e){
  //         case RawSocketEvent.READ:
  //           Datagram dg = socket.receive();
  //           if(dg != null){
  //             dg.data.forEach((x) {
                
  //             });
  //           }
  //       }
  //     });
  //   });