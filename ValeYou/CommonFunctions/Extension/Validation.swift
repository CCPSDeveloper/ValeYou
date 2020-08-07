//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

//MARK:- Alert Types
enum AlertType: String {
    case success = "Success"
    case apiFailure = "Error"
    case validationFailure = "MEX"
    var title: String {
        return NSLocalizedString(self.rawValue, comment: "")
    }
    
    var color: UIColor {
        return Color.colorPrimary.value
        
//            switch self {
//            case .validationFailure:
//              return Color.buttonBlue.value
//            case .apiFailure:
//              return Color.gradient1.value
//            case .success:
//              return Color.success.value
//            }
    }
}

//MARK:- Alert messages to be appeared on failure
class AlertMessage {
    
    static var EMPTY_SSM = "".localize
    static var PICTURE_NOTSELECTED = "PROFILE_PICTURE_UNSELECTED".localize
    static var EMPTY_EMAIL_PHONE = "EMPTY_EMAIL_PHONE".localize
    static var EMPTY_EMAIL = "EMPTY_EMAIL".localize
    static var INVALID_EMAIL = "INVALID_EMAIL".localize
    static var INVALID_PHONE = "INVALID_PHONE".localize
    
    static var NEW_PASSWORD_SENT = "NEW_PASSWORD_SENT".localize
    
    static var EMPTY_PASSWORD = "EMPTY_PASSWORD".localize
    static var EMPTY_PHONE = "EMPTY_PHONE".localize
    static var INVALID_PASSWORD = "INVALID_PASSWORD".localize
    static var PASSWORD_NOT_MATCH = "PASSWORD_NOT_MATCH".localize
    static var EMPTY_NAME = "EMPTY_NAME".localize
    static var CATEGORY_EMPTY = "CATEGORY_EMPTY".localize
    
    static var ACCEPT_TERMS = "Please accept terms and policies".localize

    static var INVALID_NAME = "INVALID_NAME".localize
    static var EMPTY_CURRENT_PSW = "EMPTY_CURRENT_PSW".localize
    static var EMPTY_NEW_PSW = "EMPTY_NEW_PSW".localize
    static var INVALID_CURRENT_PSW = "INVALID_CURRENT_PSW".localize
    static var INVALID_NEW_PSW = "INVALID_NEW_PSW".localize
    static var EMPTY_OTP = "EMPTY_OTP".localize
    static var REQUIRED_EMPTY = "REQUIRED_FIELDS_EMPTY".localize
    static var CARD_INVALID = "CARD_INVALID".localize
    static var EXPIRY_INVALID = "INVALID_EXPIRY".localize
    static var INVALID_CVV = "INVALID_CVV".localize
    
    
    
}

//MARK:- Check validation failed or not
enum Valid {
    case success
    case failure(AlertType, AlertMessage)
}

//MARK:- RegExes used to validate various things
enum RegEx: String {
    case EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" // Email
    case PASSWORD = "^.{6,100}$" // Password length 6-100
    case NAME = "^[A-Z]+[a-zA-Z]*$" // SandsHell
    case PHONE_NO = "(?!0{5,15})^[0-9]{5,15}" // PhoneNo 5-15 Digits
    case EMAIL_OR_PHONE = ""
    case All = "ANY_TEXT"
}

//MARK:- Validation Type Enum to be used with value that is to be validated when calling validate function of this class
enum ValidationType {
    case EMAIL
    case PHONE
    case EMAIL_OR_PHONE
    case NAME
    case PASSWORD
    case CONFIRM_PASSWORD
    case CURRENT_PSW
    case NEW_PSW
    case LOGIN_PSW
    case EMAIL_OR_PHONE_FORGOT_PSW
}


extension String {
    
    public var isNotBlank: Bool {
        get {
            let trimmed = trimmingCharacters(in: .whitespacesAndNewlines)
            return !trimmed.isEmpty
        }
    }
}
