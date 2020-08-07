//
//  Login.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 10/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class Login:Codable{
    var success:Int?
    var code:Int?
    var msg:String?
    var data:LoginData?
}

class LoginData:Codable{
    var status: Int?
      var id : Int?
      var description : String?
      var lastName : String?
      var firstName : String?
      var phone : String?
      var state : String?
      var age : Int?
      var dob : Int?
      var countryCode : String?
      var socialSecurityNo : String?
      var image : String?
      var driverLicence : String?
      var city : String?
      var address : String?
      var authKey : String?
      var subCategories : [UserSubCategories]?
      var email : String?
      var online : Int?
      var paypalId : String?
}

class UserSubCategories:Codable{
    
      var subCategoryImage : String?
      var providerId : Int?
      var subCategoryName : String?
      var id : Int?
      var categoryName : String?
      var categoryDescription : String?
      var categoryId : Int?
      var subCategoryDescription : String?
      var categoryImage : String?
      var subCategoryId : Int?
    
}
