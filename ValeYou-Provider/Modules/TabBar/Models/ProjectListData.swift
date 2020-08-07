//
//  ProjectListData.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 30/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation


class ProjectListModel:Codable{
    var success:Int?
    var code:Int?
    var data:[ProjectList]?
    var msg:String?
}

class ProjectList:Codable{
    var userId:Int?
    var location:String?
    var longitude:Double?
    var id:Int?
    var distance:Double?
    var title:String?
    var bid_price:Int?
    var latitude:Double?
    var description:String?
}

