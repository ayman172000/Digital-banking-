import 'package:app/screens/add_customer.dart';
import 'package:app/screens/search_customer.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';

import 'login_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  Future<void> _signOut() async {
    await FirebaseAuth.instance.signOut();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              _signOut();
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const LoginScreen(),
                ),
              );
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          children: [
            const SizedBox(
              height: 100,
            ),
            Container(
              width: 300,
              child: TextButton.icon(
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                ),
                onPressed: () {
                  Navigator.push(
    context,
    MaterialPageRoute(builder: (context) => const SearchCustomer()),
  );
                },
                label: const Text(
                  "Search customer",
                  style: TextStyle(fontSize: 25),
                ),
                icon: const Icon(
                  Icons.search,
                  size: 50,
                ),
              ),
            ),

             const SizedBox(
              height: 30,
            ),
            Container(
              width: 300,
              child: TextButton.icon(
                
                style: ButtonStyle(
                  
                  backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                ),
                onPressed: () {
                  Navigator.push(
    context,
    MaterialPageRoute(builder: (context) => const AddCustomer()),
  );
                },
                label: const Text(
                  "Add customer",
                  style: TextStyle(fontSize: 25),
                ),
                icon: const Icon(
                  Icons.group_add,
                  size: 50,
                ),
              ),
            ),
             const SizedBox(
              height: 30,
            ),

            Container(
              width: 300,
              child: TextButton.icon(
                
                style: ButtonStyle(
                  
                  backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                ),
                onPressed: () {
                  
                },
                label: const Text(
                  "    Accounts",
                  style: TextStyle(fontSize: 25),
                ),
                icon: const Icon(
                  Icons.group,
                  size: 50,
                ),
              ),
            ),
        
          ],
        ),
      ),
    );
  }
}
