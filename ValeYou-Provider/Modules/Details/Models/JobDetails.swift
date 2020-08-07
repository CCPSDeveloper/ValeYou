// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let jobDetails = try? newJSONDecoder().decode(JobDetails.self, from: jsonData)

import Foundation

// MARK: - JobDetails
struct JobDetails: Codable {
    let success, code: Int
    let data: JobDetailsData
    let msg: String
}

// MARK: - DataClass
struct JobDetailsData: Codable {
    let id: Int
    let startPrice, endPrice, dataDescription: String
    let orderCategories: [OrderCategory]?
    let time: String
    let bidPrice, type: Int
    let title: String
    let jobType: Int
    let date, endTime, location: String
    let orderImages: [OrderImage]?
    let startTime: String
    let status: Int

    enum CodingKeys: String, CodingKey {
        case id, startPrice, endPrice
        case dataDescription = "description"
        case orderCategories, time
        case bidPrice = "bid_price"
        case type, title, jobType, date, endTime, location, orderImages, startTime, status
    }
}

// MARK: - OrderCategory
struct OrderCategory: Codable {
    let id, categoryID: Int
    let category: Category?
    let subCategory: [SubCategoryElement]

    enum CodingKeys: String, CodingKey {
        case id
        case categoryID = "categoryId"
        case category, subCategory
    }
}

// MARK: - SubCategoryElement
struct SubCategoryElement: Codable {
    let id, categoryID: Int
    let subCategory: SubCategorySubCategory?

    enum CodingKeys: String, CodingKey {
        case id
        case categoryID = "categoryId"
        case subCategory
    }
}

// MARK: - SubCategorySubCategory
struct SubCategorySubCategory: Codable {
    let id: Int
    let name, image: String
}

// MARK: - OrderImage
struct OrderImage: Codable {
    let id, orderID: Int
    let updatedAt, images, createdAt: String

    enum CodingKeys: String, CodingKey {
        case id
        case orderID = "orderId"
        case updatedAt, images, createdAt
    }
}


//MARK:_ PLACE BID

// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let placeBid = try? newJSONDecoder().decode(PlaceBid.self, from: jsonData)

 
// MARK: - PlaceBid
struct PlaceBid: Codable {
    let success, code: Int
    let data: PlaceBidData
    let msg: String
}

// MARK: - DataClass
struct PlaceBidData : Codable {
    let postID, price: String

    enum CodingKeys: String, CodingKey {
        case postID = "post_id"
        case price
    }
}

