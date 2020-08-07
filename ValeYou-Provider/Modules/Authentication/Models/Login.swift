////
////  Login.swift
////  ValeYou-Provider
////
////  Created by Pankaj Sharma on 10/06/20.
////  Copyright © 2020 Pankaj Sharma. All rights reserved.
////
//
//import Foundation
//
//class Login:Codable{
//    var success:Int?
//    var code:Int?
//    var msg:String?
//    var data:LoginData?
//}
//
//class LoginData:Codable{
//
//      var id : Int?
//      var description : String?
//      var lastName : String?
//      var firstName : String?
//      var phone : String?
//      var state : String?
//      var age : Int?
//      var dob : Int?
//      var countryCode : String?
//      var socialSecurityNo : String?
//      var image : String?
//      var driverLicence : String?
//      var city : String?
//      var address : String?
//      var authKey : String?
//      var subCategories : [UserSubCategories]?
//      var email : String?
//      var online : Int?
//      var paypalId : String?
//}
//
//class UserSubCategories:Codable{
//
//      var subCategoryImage : String?
//      var providerId : Int?
//      var subCategoryName : String?
//      var id : Int?
//      var categoryName : String?
//      var categoryDescription : String?
//      var categoryId : Int?
//      var subCategoryDescription : String?
//      var categoryImage : String?
//      var subCategoryId : Int?
//
//}

// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let login = try? newJSONDecoder().decode(Login.self, from: jsonData)

import Foundation

// MARK: - Login
struct Login: Codable {
    let success, code: Int
    let data: LoginData
    let msg: String
}

// MARK: - DataClass
struct LoginData: Codable {
    let updatedAt, lastName: String
    let categoryID, id: Int
    let street, profession, deviceToken, state: String
    let tip: Int
    let resume, firstName, address, dataDescription: String
    let dob: String
    let isApprove: Int
    let socialSecurityNo, driverLicence, appartment, houseNumber: String
    let businessLicence: String
    let certificates: [JSONAny]
    let socialType: Int
    let zipCode, email, image, name: String
    let insurance: String
    let longitude: Double
    let paypalID, countryCode, city: String
    let status: Int
    let price: String
    let isRead, online, deviceType: Int
    let createdAt: String
    let businessHours: [JSONAny]
    let authKey: String
    let providerCategories: [ProviderCategory]
    let providerPortfolios, providerLanguages: [JSONAny]
    let forgotPassword: String
    let age: Int
    let socialID, phone: String
    let latitude: Double

    enum CodingKeys: String, CodingKey {
        case updatedAt, lastName
        case categoryID = "categoryId"
        case id, street, profession, deviceToken, state, tip, resume, firstName, address
        case dataDescription = "description"
        case dob, isApprove, socialSecurityNo, driverLicence, appartment, houseNumber, businessLicence, certificates, socialType, zipCode, email, image, name, insurance, longitude
        case paypalID = "paypalId"
        case countryCode, city, status, price, isRead, online, deviceType, createdAt
        case businessHours = "business_hours"
        case authKey, providerCategories, providerPortfolios, providerLanguages, forgotPassword, age
        case socialID = "socialId"
        case phone, latitude
    }
}

// MARK: - ProviderCategory
struct ProviderCategory: Codable {
    let id, providerID, categoryID: Int
    let providerSubCategories: [ProviderSubCategory]
    let category: Category

    enum CodingKeys: String, CodingKey {
        case id
        case providerID = "providerId"
        case categoryID = "categoryId"
        case providerSubCategories, category
    }
}

// MARK: - Category
struct Category: Codable {
    let id: Int
    let name, image: String
}

// MARK: - ProviderSubCategory
struct ProviderSubCategory: Codable {
    let id, providerID, subCategoryID, price: Int
    let subCategory: Category

    enum CodingKeys: String, CodingKey {
        case id
        case providerID = "providerId"
        case subCategoryID = "subCategoryId"
        case price, subCategory
    }
}

// MARK: - Encode/decode helpers

class JSONNull: Codable, Hashable {

    public static func == (lhs: JSONNull, rhs: JSONNull) -> Bool {
        return true
    }

    public var hashValue: Int {
        return 0
    }

    public init() {}

    public required init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        if !container.decodeNil() {
            throw DecodingError.typeMismatch(JSONNull.self, DecodingError.Context(codingPath: decoder.codingPath, debugDescription: "Wrong type for JSONNull"))
        }
    }

    public func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()
        try container.encodeNil()
    }
}

class JSONCodingKey: CodingKey {
    let key: String

    required init?(intValue: Int) {
        return nil
    }

    required init?(stringValue: String) {
        key = stringValue
    }

    var intValue: Int? {
        return nil
    }

    var stringValue: String {
        return key
    }
}

class JSONAny: Codable {

    let value: Any

    static func decodingError(forCodingPath codingPath: [CodingKey]) -> DecodingError {
        let context = DecodingError.Context(codingPath: codingPath, debugDescription: "Cannot decode JSONAny")
        return DecodingError.typeMismatch(JSONAny.self, context)
    }

    static func encodingError(forValue value: Any, codingPath: [CodingKey]) -> EncodingError {
        let context = EncodingError.Context(codingPath: codingPath, debugDescription: "Cannot encode JSONAny")
        return EncodingError.invalidValue(value, context)
    }

    static func decode(from container: SingleValueDecodingContainer) throws -> Any {
        if let value = try? container.decode(Bool.self) {
            return value
        }
        if let value = try? container.decode(Int64.self) {
            return value
        }
        if let value = try? container.decode(Double.self) {
            return value
        }
        if let value = try? container.decode(String.self) {
            return value
        }
        if container.decodeNil() {
            return JSONNull()
        }
        throw decodingError(forCodingPath: container.codingPath)
    }

    static func decode(from container: inout UnkeyedDecodingContainer) throws -> Any {
        if let value = try? container.decode(Bool.self) {
            return value
        }
        if let value = try? container.decode(Int64.self) {
            return value
        }
        if let value = try? container.decode(Double.self) {
            return value
        }
        if let value = try? container.decode(String.self) {
            return value
        }
        if let value = try? container.decodeNil() {
            if value {
                return JSONNull()
            }
        }
        if var container = try? container.nestedUnkeyedContainer() {
            return try decodeArray(from: &container)
        }
        if var container = try? container.nestedContainer(keyedBy: JSONCodingKey.self) {
            return try decodeDictionary(from: &container)
        }
        throw decodingError(forCodingPath: container.codingPath)
    }

    static func decode(from container: inout KeyedDecodingContainer<JSONCodingKey>, forKey key: JSONCodingKey) throws -> Any {
        if let value = try? container.decode(Bool.self, forKey: key) {
            return value
        }
        if let value = try? container.decode(Int64.self, forKey: key) {
            return value
        }
        if let value = try? container.decode(Double.self, forKey: key) {
            return value
        }
        if let value = try? container.decode(String.self, forKey: key) {
            return value
        }
        if let value = try? container.decodeNil(forKey: key) {
            if value {
                return JSONNull()
            }
        }
        if var container = try? container.nestedUnkeyedContainer(forKey: key) {
            return try decodeArray(from: &container)
        }
        if var container = try? container.nestedContainer(keyedBy: JSONCodingKey.self, forKey: key) {
            return try decodeDictionary(from: &container)
        }
        throw decodingError(forCodingPath: container.codingPath)
    }

    static func decodeArray(from container: inout UnkeyedDecodingContainer) throws -> [Any] {
        var arr: [Any] = []
        while !container.isAtEnd {
            let value = try decode(from: &container)
            arr.append(value)
        }
        return arr
    }

    static func decodeDictionary(from container: inout KeyedDecodingContainer<JSONCodingKey>) throws -> [String: Any] {
        var dict = [String: Any]()
        for key in container.allKeys {
            let value = try decode(from: &container, forKey: key)
            dict[key.stringValue] = value
        }
        return dict
    }

    static func encode(to container: inout UnkeyedEncodingContainer, array: [Any]) throws {
        for value in array {
            if let value = value as? Bool {
                try container.encode(value)
            } else if let value = value as? Int64 {
                try container.encode(value)
            } else if let value = value as? Double {
                try container.encode(value)
            } else if let value = value as? String {
                try container.encode(value)
            } else if value is JSONNull {
                try container.encodeNil()
            } else if let value = value as? [Any] {
                var container = container.nestedUnkeyedContainer()
                try encode(to: &container, array: value)
            } else if let value = value as? [String: Any] {
                var container = container.nestedContainer(keyedBy: JSONCodingKey.self)
                try encode(to: &container, dictionary: value)
            } else {
                throw encodingError(forValue: value, codingPath: container.codingPath)
            }
        }
    }

    static func encode(to container: inout KeyedEncodingContainer<JSONCodingKey>, dictionary: [String: Any]) throws {
        for (key, value) in dictionary {
            let key = JSONCodingKey(stringValue: key)!
            if let value = value as? Bool {
                try container.encode(value, forKey: key)
            } else if let value = value as? Int64 {
                try container.encode(value, forKey: key)
            } else if let value = value as? Double {
                try container.encode(value, forKey: key)
            } else if let value = value as? String {
                try container.encode(value, forKey: key)
            } else if value is JSONNull {
                try container.encodeNil(forKey: key)
            } else if let value = value as? [Any] {
                var container = container.nestedUnkeyedContainer(forKey: key)
                try encode(to: &container, array: value)
            } else if let value = value as? [String: Any] {
                var container = container.nestedContainer(keyedBy: JSONCodingKey.self, forKey: key)
                try encode(to: &container, dictionary: value)
            } else {
                throw encodingError(forValue: value, codingPath: container.codingPath)
            }
        }
    }

    static func encode(to container: inout SingleValueEncodingContainer, value: Any) throws {
        if let value = value as? Bool {
            try container.encode(value)
        } else if let value = value as? Int64 {
            try container.encode(value)
        } else if let value = value as? Double {
            try container.encode(value)
        } else if let value = value as? String {
            try container.encode(value)
        } else if value is JSONNull {
            try container.encodeNil()
        } else {
            throw encodingError(forValue: value, codingPath: container.codingPath)
        }
    }

    public required init(from decoder: Decoder) throws {
        if var arrayContainer = try? decoder.unkeyedContainer() {
            self.value = try JSONAny.decodeArray(from: &arrayContainer)
        } else if var container = try? decoder.container(keyedBy: JSONCodingKey.self) {
            self.value = try JSONAny.decodeDictionary(from: &container)
        } else {
            let container = try decoder.singleValueContainer()
            self.value = try JSONAny.decode(from: container)
        }
    }

    public func encode(to encoder: Encoder) throws {
        if let arr = self.value as? [Any] {
            var container = encoder.unkeyedContainer()
            try JSONAny.encode(to: &container, array: arr)
        } else if let dict = self.value as? [String: Any] {
            var container = encoder.container(keyedBy: JSONCodingKey.self)
            try JSONAny.encode(to: &container, dictionary: dict)
        } else {
            var container = encoder.singleValueContainer()
            try JSONAny.encode(to: &container, value: self.value)
        }
    }
}
