//
//  LanguagesModel.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 10/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class LanguagesModel:Codable{
    var success:Int?
    var code:Int?
    var data:[LanguageData]?
    var msg:String?
}

class LanguageData:Codable{
    var status:Int?
    var providerId:Int?
    var id:Int?
    var updatedAt:String?
    var type:String?
    var createdAt:String?
    var name:String?
}
