//
//  PortfolioModel.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 10/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class PortfolioModel:Codable{
    var success:Int?
    var code:Int?
    var data:[PortfolioData]?
    var msg:String?
}

class PortfolioData:Codable{
    var status:Int?
    var providerId:Int?
    var id:Int?
    var title:String?
    var image:String?
    var updatedAt:String?
    var description:String?
    var createdAt:String?
}
