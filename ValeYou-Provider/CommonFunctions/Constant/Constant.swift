//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

struct SDKs{
    
    struct google{
        static let clientId = "270403256545-saqtedl2qa8uur09dkvblbigkv1aofgs.apps.googleusercontent.com"
    }
    
}

enum Gender:String{
    case male = "Male"
    case female = "Female"
    
    var string:String{
           return NSLocalizedString(self.rawValue, comment: "")
       }
    
}

enum MaritalStatus:String{
    case married = "Married"
    case unMarried = "Unmarried"
    
    var string:String{
        return NSLocalizedString(self.rawValue, comment: "")
    }
}

enum JobType:String{
    case fullTime = "Full Time"
    case partTime = "Part Time"
    
    var string:String{
        return NSLocalizedString(self.rawValue, comment: "")
    }
}

enum PostType:String{
    case jobs = "Jobs"
    case events = "Events"
    case business = "Business"
    case ads = "Ads"
}

//Cell Identifiers
enum CellIdentifiers:String{
    case OnboardingCell = "OnboardingCell"
    case CategoryCell = "CategoryCell"
    case SideCell = "SideCell"
    case JobCell = "JobCell"
    case ReviewCell = "ReviewCell"
    case NotificationCell = "NotificationCell"
    case InboxCell = "InboxCell"
    case JobPhotoCell = "JobPhotoCell"
    case SubCategoryCell = "SubCategoryCell"
    case FavouriteCell = "FavouriteCell"
    case FAQCell = "FAQCell"
    case ProviderCell = "ProviderCell"
    case ProviderListCell = "ProviderListCell"
    case ProfileOptionCell = "ProfileOptionCell"
    case LanguageCell = "LanguageCell"
    case PortfolioCell = "PortfolioCell"
    case IdentityCell = "IdentityCell"
    case CertificateCell = "CertificateCell"
}


enum BarItems:String{
    case home = "Home"
    case aboutUs = "About Us"
    case findFriends = "Find New Friends"
    case findBusiness = "Find a Business"
    case findEvents = "Find Local Events"
    case findJob = "Find a Job"
    case postBusiness = "Post/Edit Business Profile"
    case postEvent = "Post/Edit an Event"
    case postJob = "Post/Edit a Job"
    case friends = "Your Friends"
    case favorites = "Your Favourites"
    case messages = "Messages"
    case notifications = "Notifications"
    case advertisement = "Advertise on MEX"
    case payments = "Payments"
    case settings = "Settings"
    case signOut = "Sign Out"
    
    var string:String{
        return NSLocalizedString(self.rawValue, comment: "")
    }
}

let kDefaultDateFormat = "dd/MM/yyyy h:mm a"
