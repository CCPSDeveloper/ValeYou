// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let getNotifications = try? newJSONDecoder().decode(GetNotifications.self, from: jsonData)

import Foundation

// MARK: - GetNotifications
struct GetNotifications: Codable {
    let success, code: Int
    let msg: String
    let data: [GetNotificationsData]
}

// MARK: - Datum
struct GetNotificationsData: Codable {
    let id, userID, type, user2ID: Int
    let isSeen: Int
    let message: String
    let createdAt: Int
    let providerImage, firstName, lastName: String

    enum CodingKeys: String, CodingKey {
        case id
        case userID = "userId"
        case type
        case user2ID = "user2Id"
        case isSeen, message, createdAt, providerImage, firstName, lastName
    }
}
