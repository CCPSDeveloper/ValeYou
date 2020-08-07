// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let getProviderDetails = try? newJSONDecoder().decode(GetProviderDetails.self, from: jsonData)

import Foundation

// MARK: - GetProviderDetails
struct GetProviderDetails: Codable {
    let success, code: Int
    let msg: String
    let data: GetProviderDetailsData
}

// MARK: - DataClass
struct GetProviderDetailsData: Codable {
    let id: Int
    let name, phone, image, dataDescription: String
    let address, email: String
    let likeStatus: Int
    let averageRating: Double
    let totalRatings, totalprovidedservices: Int
    let firstName, lastName, countryCode: String
    let providerCategories: [ProviderCategory]
    let providerLanguage: [LanguageData]//[ProviderLanguage]
    let providerPortfolio, providerCertificate: [Provider]
    let providerBusinessHours: [String]?

    enum CodingKeys: String, CodingKey {
        case id, name, phone, image
        case dataDescription = "description"
        case address, email
        case likeStatus = "like_status"
        case averageRating = "average_rating"
        case totalRatings = "total_ratings"
        case totalprovidedservices, firstName, lastName, countryCode, providerCategories, providerLanguage, providerPortfolio, providerCertificate, providerBusinessHours
    }
}

// MARK: - ProviderCategory
struct ProviderCategory: Codable {
    let id, categoryID, providerID: Int
    let category: Category
    let subCategories: [SubCategory]?

    enum CodingKeys: String, CodingKey {
        case id
        case categoryID = "categoryId"
        case providerID = "providerId"
        case category, subCategories
    }
}

// MARK: - Category
//struct Category: Codable {
//    let id: Int
//    let name, image: String
//}

// MARK: - SubCategory
//struct SubCategory: Codable {
//    let id, categoryID, providerID: Int
//    let subCategory: Category
//
//    enum CodingKeys: String, CodingKey {
//        case id
//        case categoryID = "categoryId"
//        case providerID = "providerId"
//        case subCategory
//    }
//}

// MARK: - Provider
struct Provider: Codable {
    let id, providerID: Int
    let image, title, providerDescription: String
    let status: Int
    let createdAt, updatedAt: String

    enum CodingKeys: String, CodingKey {
        case id
        case providerID = "providerId"
        case image, title
        case providerDescription = "description"
        case status, createdAt, updatedAt
    }
}

// MARK: - ProviderLanguage
struct ProviderLanguage: Codable {
    let id, providerID: Int
    let name, type: String
    let status: Int
    let createdAt, updatedAt: String

    enum CodingKeys: String, CodingKey {
        case id
        case providerID = "providerId"
        case name, type, status, createdAt, updatedAt
    }
}

 
