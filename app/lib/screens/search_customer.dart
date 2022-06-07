import 'dart:convert';

import 'package:app/models/customer.dart';
import 'package:app/screens/home_screen.dart';
import 'package:app/widgets/search_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_slidable/flutter_slidable.dart';

class SearchCustomer extends StatefulWidget {
  const SearchCustomer({Key? key}) : super(key: key);

  @override
  State<SearchCustomer> createState() => _SearchCustomerState();
}

class _SearchCustomerState extends State<SearchCustomer> {
  List<Customer> _customers = [];
  late List<Customer> customers;

  Future<void> fetchCustomer() async {
    final response =
        await http.get(Uri.parse('http://10.0.2.2:8085/customers'));

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.

      final data = json.decode(response.body);

      for (var customer in data) {
        _customers.add(Customer.fromMap(customer));
      }
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load Audio');
    }
  }

  deleteCustomer(int id) async {
    http.delete(
      // ignore: prefer_interpolation_to_compose_strings
      Uri.parse('http://10.0.2.2:8085/customers/' + id.toString()),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    ).then((response) {
      if (response.statusCode == 200) {}

      List<Customer> customers = [];
      for (Customer customer in _customers) {
        if (customer.id != id) {
          customers.add(customer);
        }
      }
      setState(() {
        _customers = customers;
      });
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    fetchCustomer();
    customers = _customers;
  }

  String query = "";

  Widget buildSearch() => SearchWidget(
        hintText: 'Enter a name',
        onChanged: search,
        text: query,
      );

  Widget buildCustomer(Customer customer) => Slidable(
        actionPane: const SlidableDrawerActionPane(),
        // ignore: prefer_const_literals_to_create_immutables
        secondaryActions: [
          IconSlideAction(
            caption: 'accounts',
            color: Colors.green,
            icon: Icons.account_circle,
            onTap: () {
              print("object");
            },
          ),
          IconSlideAction(
            onTap: () {
              deleteCustomer(customer.id!);
            },
            caption: 'delete',
            color: Colors.red,
            icon: Icons.delete,
          ),
        ],
        child: ListTile(
          leading: Text(customer.id.toString()),
          title: Text(customer.name ?? ''),
          subtitle: Text(customer.email ?? ''),
        ),
      );

  void search(String query) {
    final customers = _customers.where((customer) {
      final nameLower = customer.name!.toLowerCase();

      final searchLower = query.toLowerCase();

      return nameLower.contains(searchLower);
    }).toList();

    setState(() {
      this.query = query;
      this.customers = customers;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: TextButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const HomeScreen()),
              );
            },
            child: const Text(
              "Home",
              style: TextStyle(
                  color: Colors.white,
                  fontSize: 20,
                  fontWeight: FontWeight.bold),
            )),
      ),
      body: Column(
        children: [
          buildSearch(),
          Expanded(
            child: ListView.builder(
                itemCount: customers.length,
                itemBuilder: (context, index) {
                  final customer = customers[index];
                  return buildCustomer(customer);
                }),
          ),
        ],
      ),
    );
  }
}
