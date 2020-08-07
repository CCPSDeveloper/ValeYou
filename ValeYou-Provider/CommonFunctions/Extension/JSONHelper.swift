//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

class JSONHelper<T: Codable> {
  
  init() {
    
  }
  
    func getCodableModel(data: Data) -> T? {
        var modelData : T?
        do {
            let model = try JSONDecoder().decode(T.self, from: data)
            modelData = model
        }catch (let error ){
            print("Error: ",error)
            Toast.shared.showAlert(type: .apiFailure, message: error.localizedDescription)
        }
        return modelData
    }
  
  func getData(model: T) -> Data? {
    guard let data: Data = try? JSONEncoder().encode(model) else {
      return nil
    }
    return data
  }
}
