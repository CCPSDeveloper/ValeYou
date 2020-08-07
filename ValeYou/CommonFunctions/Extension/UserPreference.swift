//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class UserPreference {
  
  let DEFAULTS_KEY = "MEX"
  let loggedIn = "Logged"
 
  static let shared = UserPreference()
//
  var data : LoginData? {
    get{
      return fetchData()
    }
    set{
      if let value = newValue {
        saveData(value)
      } else {
        removeData()
      }
    }
  }
    
    var isLogged:Bool?{
        get {
            return isLoggedIn()
        }set{
            if let value = newValue{
                setLoggedIn(value)
            }else{
                removeLoggedIn()
            }
        }
    }
    
    func isLoggedIn()->Bool?{
        UserDefaults.standard.value(forKey: loggedIn) as? Bool
    }
    
    func setLoggedIn(_ value:Bool){
        UserDefaults.standard.set(value, forKey: loggedIn)
    }
    
    func removeLoggedIn(){
        UserDefaults.standard.removeObject(forKey: loggedIn)
    }

  func saveData(_ value: LoginData) {
    guard let data = JSONHelper<LoginData>().getData(model: value) else {
      removeData()
      return
    }
    UserDefaults.standard.set(data, forKey: DEFAULTS_KEY)
  }
//
  func fetchData() -> LoginData? {
    guard let data = UserDefaults.standard.data(forKey: DEFAULTS_KEY) else {
      return nil
    }
    return JSONHelper<LoginData>().getCodableModel(data: data)
  }
//
  func removeData() {
    UserDefaults.standard.removeObject(forKey: DEFAULTS_KEY)
  }
    
}

