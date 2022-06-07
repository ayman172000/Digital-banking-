import 'dart:convert';

import 'package:app/models/customer.dart';
import 'package:app/screens/search_customer.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:http/http.dart' as http;

class AddCustomer extends StatefulWidget {
  const AddCustomer({Key? key}) : super(key: key);

  @override
  State<AddCustomer> createState() => _AddCustomerState();
}

class _AddCustomerState extends State<AddCustomer> {
  final TextEditingController nameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();

  Future<Customer> createCustomer(String name, String email) async {
    final response = await http.post(
      Uri.parse('http://10.0.2.2:8085/customers'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'name': name,
        'email': email,
      }),
    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      return Customer.fromMap(jsonDecode(response.body));
      
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Failed to create Customer.');
    }
  }

  @override
  Widget build(BuildContext context) {
    final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
    return Scaffold(
      appBar: AppBar(
        leading: GestureDetector(
          onTap: () {
              Navigator.pop(context);
            } ,
          child: BackButton(),
        ),
        title: Text("Add customer"),
      ),
      body: Container(
        color: Colors.white,
        child: Padding(
          padding: const EdgeInsets.all(36.0),
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                TextFormField(
                  autofocus: false,
                  controller: nameController,
                  keyboardType: TextInputType.text,
                  onSaved: (value) {
                    nameController.text = value!;
                  },
                  decoration:  InputDecoration(
                    
                    prefixIcon: const Icon(Icons.account_circle),
                    contentPadding: const EdgeInsets.fromLTRB(20, 15, 20, 15),
                    hintText: 'Name',
                    border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10),
            ),
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                TextFormField(
                  autofocus: false,
                  controller: emailController,
                  keyboardType: TextInputType.emailAddress,
                  onSaved: (value) {
                    emailController.text = value!;
                  },
                  decoration:  InputDecoration(
                    prefixIcon: const Icon(Icons.mail),
                    contentPadding: const EdgeInsets.fromLTRB(20, 15, 20, 15),
                    hintText: 'Email',
                    border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10),
            ),
                  ),
                ),
                SizedBox(
                  height: 15,
                ),
                ElevatedButton(
                  onPressed: () {
                    createCustomer(nameController.text, emailController.text);
                    Fluttertoast.showToast(msg: "Created successfully");
                    Navigator.push(
    context,
    MaterialPageRoute(builder: (context) => const SearchCustomer()),
  );
                  },
                  child: const Text('save'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
