//
//  BasicApiResponse.swift
//  ValeYou
//
//  Created by Techwin on 31/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
// MARK: - BasicResponse
struct BasicResponse: Codable {
    let success, code: Int
    let msg: String
    let data: BasicResponseData?
}

// MARK: - DataClass
struct BasicResponseData: Codable {
    
}
 
// MARK: - RespondBid
struct RespondBid: Codable {
    let success, code: Int
    let data: DataClass
    let msg: String
}

// MARK: - DataClass
struct DataClass: Codable {
    let status: Int
}

