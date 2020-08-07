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
            
        case is UserEP:
            let endpoint = self as! UserEP
            switch endpoint {
                
            case .getCategories:
                return JSONHelper<Categories>().getCodableModel(data: data)
                
            case .signup, .login, .socialLogin:
                return JSONHelper<Login>().getCodableModel(data: data)
          
            case .getMapList:
                return JSONHelper<GetProviderList>().getCodableModel(data: data)
         
            case .userJobList:
                return JSONHelper<UserJobListModel>().getCodableModel(data: data)

            case .getJobDetails:
                return JSONHelper<JobDetails>().getCodableModel(data: data)
                
            case .addToFavourite:
                return JSONHelper<BasicResponse>().getCodableModel(data: data)
                
            case .userGetProviderDetail:
                return JSONHelper<GetProviderDetails>().getCodableModel(data: data)
                
                case .getFavouriteList:
                             return JSONHelper<GetFavourites>().getCodableModel(data: data)
                
                case .getNotifications:
                                         return JSONHelper<GetNotifications>().getCodableModel(data: data)
     
                case .respondBid:
                    return JSONHelper<RespondBid>().getCodableModel(data: data)
            default:
                break
            }
        default:
            return nil
        }
        
        return nil
    }
}
