//
//  Categories.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 09/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class Categories:Codable{
    var success:Int?
    var code:Int?
    var msg:String?
    var data:[CategoriesData]?
}

class CategoriesData:Codable{
    var id:Int?
    var name:String?
    var image:String?
    var description:String?
    var subCategories:[SubCategories]?
    
    var selectedSubCategory:[SubCategories]?
    var selectedSubCategoryPrice:String?
    var selectedSubId:Int?
    
    init() {
        
    }

}

class SubCategories:Codable{
    var id:Int?
    var name:String?
    var image:String?
    var categoryId:Int?
    var description:String?
    var price:String?
}

          
