//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

internal struct APIConstant{
    
    static let basePath = "http://3.17.254.50:4999/apis/"
    static let inboxBasePath = "http://3.17.254.50:4999/api/"
    static let mediaBasePath = "http://3.17.254.50:4999/upload/"
   
        
    //Common APIS
    static let signup = "provider_signup"
    static let login = "provider_login"
    static let logout = "provider_logout"
    static let changePassword = "provider_change_password"
    static let forgotPassword = "provider_forgot_password"
    static let socialLogin = "provider_social_login"
    
    static let providerMapListing = "provider_map_list"
    static let getFavouriteList = "provider_get_favourite_list"
    static let addToFavourite = "provider_add_to_fav_list"
    
    static let myJobs = "provider_my_jobs"
    
    static let getHomeListing = "provider_get_home_listing"
    static let provider_availability = "provider_availibility"
    static let jobDetails = "provider_job_details"
    static let getProfile = "provider_get_profile"
    static let editProfile = "provider_edit_profile"
    static let allContent = "provider_all_content"
    static let placeBid = "provider_place_bid"
    static let updateBid = "provider_update_bid"
    static let myReviews = "provider_my_reviews"
    static let rateUser  = "provider_rate_user"
    static let getCategories = "provider_get_categories"
    static let jobHistory = "provider_job_history"
    static let notifications = "provider_notifications"
    static let startJob = "provider_start_job"
    
    static let getLanguage = "provider_get_language"
    static let addLanguage = "provider_add_language"
    static let updateLanguage = "provider_edit_language"
    static let deleteLanguage = "provider_delete_language"
    
    
    static let addBusinessHour = "provider_add_business_hours"
    static let getBusinessHour = "provider_get_business_hours"
    
    static let addCertificate = "provider_add_certificate"
    static let editCertificate = "provider_edit_certificate"
    static let deleteCertificate = "provider_delete_certificate"
    static let getCertificate = "provider_get_certificate"
    
    static let addPortfolio = "provider_add_portfolio"
    static let getPortfolio = "provider_get_portfolio"
    static let editPortfolio = "provider_edit_portfolio"
    static let deletePortfolio = "provider_delete_portfolio"
    
    static let readNotifications = "provider_read_notification"
    static let filterSearch = "provider_filter"

}



class AuthHeader{
    
    static func makeAuth()->[String:String]{

          let auth = ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ="]
          
              return auth
     }
}

class UserHeaders{
    
 static func makeAuth()->[String:String]{
        
    
    let auth = ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey]
    
        return auth
     }
}
