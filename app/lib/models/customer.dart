

class Customer {
  int? id;
  String? name;
  String? email;

  

  Customer(
      {this.id,
      this.name,
      this.email,
     });

  // receiving data from server
  factory Customer.fromMap(map) {
    return Customer(
      id: map['id'],
      name: map['name'],
      email: map['email'],
   
    );
  }

  // sending data to our server
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'email': email,
     
    };
  }
}
