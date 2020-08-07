//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import Moya

extension TargetType {
    
    //Method to parse data in models according to endpoints
    func parseModel(data: Data) -> Any? {
        
        switch self {
            
        case is ProviderEP:
            let endpoint = self as! ProviderEP
            switch endpoint {
            case .getJobDetails:
                 return JSONHelper<JobDetails>().getCodableModel(data: data)
            case .placeBid:
                return JSONHelper<PlaceBid>().getCodableModel(data: data)
            case .getCategories:
                return JSONHelper<Categories>().getCodableModel(data: data)
                
            case .signup,.login:
                return JSONHelper<Login>().getCodableModel(data: data)
            case .getMapList:
                return JSONHelper<ProjectListModel>().getCodableModel(data: data)
            case .getPortfolio,.getCertificates:
                return JSONHelper<PortfolioModel>().getCodableModel(data: data)
            case .getLanguage:
                return JSONHelper<LanguagesModel>().getCodableModel(data: data)
            default:
                break
            }
        default:
            return nil
        }
        
        return nil
    }
}
