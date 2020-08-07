 
// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let welcome = try? newJSONDecoder().decode(Welcome.self, from: jsonData)

import Foundation

// MARK: - Welcome
struct UserJobListModel: Codable {
    let success, code: Int
    let data: [UserJob]
    let msg: String
}

// MARK: - Datum
struct UserJob: Codable {
    let id: Int
    let datumDescription, state, startPrice, endPrice: String
    let type: Int
    let title: String
    let userID: Int
    let location, date, endTime: String
    let providerID: Int
    let orderImages: [OrderImage]
    let startTime: String
    let status: Int
    let jobType: Int

    enum CodingKeys: String, CodingKey {
        case id
        case datumDescription = "description"
        case state, startPrice, endPrice, type, title
        case userID = "userId"
        case location, date, endTime
        case providerID = "providerId"
        case jobType = "jobType"
        case orderImages, startTime, status
    }
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
