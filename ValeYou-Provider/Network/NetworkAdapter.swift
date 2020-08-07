//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//
import Foundation
import Moya


private func JSONResponseDataFormatter(_ data: Data) -> Data {
    do {
        let dataAsJSON = try JSONSerialization.jsonObject(with: data)
        let prettyData =  try JSONSerialization.data(withJSONObject: dataAsJSON, options: .prettyPrinted)
        return prettyData
    } catch {
        return data // fallback to original data if it can't be serialized.
    }
}

extension TargetType {
    
    func provider<T: TargetType>() -> MoyaProvider<T> {
        return MoyaProvider<T>(plugins: [(NetworkLoggerPlugin(verbose: true, responseDataFormatter: JSONResponseDataFormatter))])
    }
    
    
    func request(loader:Bool,success successCallBack: @escaping (Any?) -> Void, error errorCallBack: ((String?) -> Void)? = nil) {
        

        if loader{
           handleLoader()
        }
        
        provider().request(self) { (result) in
            //Hide Loader after getting response
            UIApplication.shared.endIgnoringInteractionEvents()
            
            self.hideLoader()
            
            switch result {
            case .success(let response):
                switch response.statusCode {
                case 200, 201,202:
                    do{
                         let model = self.parseModel(data: response.data)
                        successCallBack(model)
                    }catch{
                        errorCallBack?("Empty Response")
                    }
                    
                case 401:
                    do {
                        let json = try JSONSerialization.jsonObject(with: response.data, options: []) as? [String : Any]
                        Toast.shared.showAlert(type: .apiFailure, message: /(json?["message"] as? String))
                        errorCallBack?(/(json?["msg"] as? String))
                    } catch {
                        Toast.shared.showAlert(type: .apiFailure, message: error.localizedDescription)
                    }
                    Router.shared.setLoginAsRoot()
                    //perfoem session expired actions
                    
                case 400,404, 409, 500:
                    do {
                        let json = try JSONSerialization.jsonObject(with: response.data, options: []) as? [String : Any]
                        Toast.shared.showAlert(type: .apiFailure, message: /(json?["msg"] as? String))
                        errorCallBack?(/(json?["msg"] as? String))
                    } catch {
                        Toast.shared.showAlert(type: .apiFailure, message: error.localizedDescription)
                    }
                default:
                    Toast.shared.showAlert(type: .apiFailure, message: "Error Default")
                }
            case .failure(let error):
                Toast.shared.showAlert(type: .apiFailure, message: error.localizedDescription)
                errorCallBack?(error.localizedDescription)
            }
        }
    }
    
    //MARK: - Show Loader
          func showLoader() {
              DispatchQueue.main.async {
                  Loader.shared.start()
              }
          }
       //MARK: - Hide Loader
          func hideLoader() {
              DispatchQueue.main.async {
                  Loader.shared.stop()
              }
          }
    
    func handleLoader() {
        switch self {

        default:
            UIApplication.shared.beginIgnoringInteractionEvents()
            self.showLoader()
        }
        
    }
}
