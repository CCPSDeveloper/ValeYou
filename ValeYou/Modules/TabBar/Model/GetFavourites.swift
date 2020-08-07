// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let getFavourites = try? newJSONDecoder().decode(GetFavourites.self, from: jsonData)

import Foundation

// MARK: - GetFavourites
struct GetFavourites: Codable {
    let success, code: Int
    let msg: String
    let data: [GetFavouritesData]
}

// MARK: - GetFavouritesData
struct GetFavouritesData: Codable {
    let id, userID, providerID, status: Int
    let providerfirstName, providerlastName, providerImage, datumDescription: String
    let avgRating: Int
    
    enum CodingKeys: String, CodingKey {
        case id
        case userID = "userId"
        case providerID = "providerId"
        case status, providerfirstName, providerlastName, providerImage
        case datumDescription = "description"
        case avgRating = "avg_rating"
    }
}
