// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let getProviderList = try? newJSONDecoder().decode(GetProviderList.self, from: jsonData)

import Foundation

// MARK: - GetProviderList
struct GetProviderList: Codable {
    let success, code: Int
    let msg: String
    let data: [GetProviderListData]
}

// MARK: - Datum
struct GetProviderListData: Codable {
    let id: Int
    let firstName, lastName, datumDescription, image: String
    let address: String
    let latitude, longitude: Double
    let profession: String
    let online: Int
    let state: String
    let distance, avgRating: Double
    let fav, totalJobs: Int

    enum CodingKeys: String, CodingKey {
        case id, firstName, lastName
        case datumDescription = "description"
        case image, address, latitude, longitude, profession, online, state, distance
        case avgRating = "avg_rating"
        case fav
        case totalJobs = "total_jobs"
    }
}
