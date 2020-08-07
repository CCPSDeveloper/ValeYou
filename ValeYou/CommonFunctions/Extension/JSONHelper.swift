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
        var decoded : T?
        do {
            let model = try JSONDecoder().decode(T.self, from: data)
            decoded =  model
        }catch(let error){
            print(error)
            Toast.shared.showAlert(type: .apiFailure, message: error.localizedDescription)
        }
        return decoded
    }
    
    func getData(model: T) -> Data? {
        guard let data: Data = try? JSONEncoder().encode(model) else {
            return nil
        }
        return data
    }
}
