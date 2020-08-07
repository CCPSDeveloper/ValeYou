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
    static let signup = "user_signup"
    static let login = "user_login"
    static let logout = "user_logout"
    static let changePassword = "user_change_password"
    static let forgotPassword = "user_forgot_password"
    static let socialLogin = "user_social_login"
    
    static let userJobList = "user_job_list"
    static let userAddPost = "user_add_post"

    static let getCategories = "user_get_categories"

    static let providerMapListing = "user_provider_nearme"//provider_map_list"
    static let getFavouriteList = "user_get_favourite_list"
    static let addToFavourite = "user_add_to_fav_list"
    static let userGetProviderDetail = "user_get_provider_detail"
    static let myJobs = "provider_my_jobs"
    static let jobDetails = "user_post_details"//"provider_job_details"
    static let respondBid = "user_accept_reject_bid"
    
    static let getHomeListing = "provider_get_home_listing"
    static let provider_availability = "provider_availibility"
    static let getProfile = "user_get_profile"
    static let editProfile = "user_edit_profile"
    static let allContent = "provider_all_content"
    static let placeBid = "provider_place_bid"
    static let updateBid = "provider_update_bid"
    static let myReviews = "provider_my_reviews"
    static let rateUser  = "provider_rate_user"
    static let jobHistory = "provider_job_history"
    static let notifications = "user_get_notifications"
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
