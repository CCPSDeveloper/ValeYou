// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let jobDetails = try? newJSONDecoder().decode(JobDetails.self, from: jsonData)

import Foundation

// MARK: - JobDetails
struct JobDetails: Codable {
    let success, code: Int
    let msg: String
    let data: JobDetailsData
}

// MARK: - DataClass
struct JobDetailsData: Codable {
    let id: Int
    let date: String
    let providerID, userID: Int
    let title, startTime, endTime, startPrice: String
    let endPrice, state, dataDescription, location: String
    let status: Int
    let time: String
    let type: Int
    let orderCategories: [OrderCategory]
    let orderImages: [OrderImage]
    let bids: [Bid]
    let jobType : Int

    enum CodingKeys: String, CodingKey {
        case id, date
        case providerID = "providerId"
        case userID = "userId"
        case title, startTime, endTime, startPrice, endPrice, state
        case dataDescription = "description"
        case jobType = "jobType"
        case location, status, time, type, orderCategories, orderImages, bids
    }
}

 

// MARK: - Bid
struct Bid: Codable {
    let id: Int
       let distance: Double
       let avgrating, price: Int
       let providerLastName: String
       let orderID, providerID: Int
       let providerFirstName, providerImage: String
       let createdAt, status: Int
       let providerDescription: String

       enum CodingKeys: String, CodingKey {
           case id, distance, avgrating, price, providerLastName
           case orderID = "orderId"
           case providerID = "providerId"
           case providerFirstName, providerImage, createdAt, status, providerDescription
       }
}

// MARK: - OrderCategory
struct OrderCategory: Codable {
    let id, categoryID: Int
    let category: Category
    let subCategory: [SubCategory]

    enum CodingKeys: String, CodingKey {
        case id
        case categoryID = "categoryId"
        case category, subCategory
    }
}

// MARK: - Category
struct Category: Codable {
    let id: Int
    let name, image: String
}

// MARK: - SubCategory
struct SubCategory: Codable {
    let id, categoryID: Int
    let subCategory: Category

    enum CodingKeys: String, CodingKey {
        case id
        case categoryID = "categoryId"
        case subCategory
    }
}

// MARK: - Encode/decode helpers
 
