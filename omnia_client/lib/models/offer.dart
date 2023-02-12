class Offer {
  final String id;
  final String category;
  final String description;
  String? pictureUrl;
  final String title;

  Offer({
    required this.id,
    required this.category,
    required this.description,
    this.pictureUrl,
    required this.title,
  });

  Map<String, dynamic> toJson() {
    return {
      "category": category,
      "description": description,
      "picture_url": pictureUrl,
      "title": title,
      "id": id
    };
  }
  factory Offer.fromJson(Map<String,dynamic> json){
    return Offer(
      category: json['category'],
      description: json['description'],
      id: json['id'],
      pictureUrl: json['picture_url'],
      title: json['title']
    );
  }
}
