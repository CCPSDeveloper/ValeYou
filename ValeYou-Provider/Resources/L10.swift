//
//  L102Language.swift
//  Localization102
//
//  Created by Moath_Othman on 2/24/16.
//  Copyright © 2016 Moath_Othman. All rights reserved.
//

import UIKit

// constants
let APPLE_LANGUAGE_KEY = "AppleLanguages"

/// L102Language

class L102Language: NSObject {
    
    
    /// get current Apple language
    class func currentAppleLanguage() -> String {
        
        let userdef = UserDefaults.standard
        let langArray = userdef.object(forKey: APPLE_LANGUAGE_KEY) as! NSArray
        let current = langArray.firstObject as! String
        let endIndex = current.startIndex
        let currentWithoutLocale = current.substring(to: current.index(endIndex, offsetBy: 2))
        print(currentWithoutLocale)
        return currentWithoutLocale
    }
    
    class func currentAppleLanguageFull() -> String {
        
        let userdef = UserDefaults.standard
        let langArray = userdef.object(forKey: APPLE_LANGUAGE_KEY) as! NSArray
        let current = langArray.firstObject as! String
        
        return current
    }
    
    /// set @lang to be the first in Applelanguages list
    class func setAppleLAnguageTo(lang: String) {
        
        let userdef = UserDefaults.standard
        userdef.set([lang,currentAppleLanguage()], forKey: APPLE_LANGUAGE_KEY)
        
        userdef.synchronize()
    }
    
    @objc class var isRTL : Bool {
        return L102Language.currentAppleLanguage() == "ar"
    }
}

func MethodSwizzleGivenClassName1(cls: AnyClass, originalSelector: Selector, overrideSelector: Selector) {
    
    let origMethod: Method = class_getInstanceMethod(cls, originalSelector)!
    
    let overrideMethod: Method = class_getInstanceMethod(cls, overrideSelector)!
    
    if (class_addMethod(cls, originalSelector, method_getImplementation(overrideMethod), method_getTypeEncoding(overrideMethod))) {
        
        class_replaceMethod(cls, overrideSelector, method_getImplementation(origMethod), method_getTypeEncoding(origMethod))
        
    } else {
        method_exchangeImplementations(origMethod, overrideMethod);
    }
}




extension Bundle {
    
    @objc func specialLocalizedStringForKey(_ key: String, value: String?, table tableName: String?) -> String {
        
        if self == Bundle.main {
            
            let currentLanguage = L102Language.currentAppleLanguage()
            var bundle = Bundle();
            
            if let _path = Bundle.main.path(forResource: L102Language.currentAppleLanguageFull(), ofType: "lproj") {
                
                bundle = Bundle(path: _path)!
                
            }else if let _path = Bundle.main.path(forResource: currentLanguage, ofType: "lproj") {
                    
                    bundle = Bundle(path: _path)!
                
                } else {
                
                    let _path = Bundle.main.path(forResource: "Base", ofType: "lproj")!
                    bundle = Bundle(path: _path)!
            }
            
            return (bundle.specialLocalizedStringForKey(key, value: value, table: tableName))
            
        } else {
            
            return (self.specialLocalizedStringForKey(key, value: value, table: tableName))
        }
    }
}

class L102Localizer: NSObject {
    
    class func DoTheMagic() {
        
        MethodSwizzleGivenClassName1(cls: Bundle.self, originalSelector: #selector(Bundle.localizedString(forKey:value:table:)), overrideSelector: #selector(Bundle.specialLocalizedStringForKey(_:value:table:)))
        
    }
    
    class func DoMagic(){
        
    }
}


extension UIApplication {
    
    var cstm_userInterfaceLayoutDirection : UIUserInterfaceLayoutDirection {
        
        get {
            
            var direction = UIUserInterfaceLayoutDirection.leftToRight
            
            if L102Language.currentAppleLanguage() == "ar" {
                
                direction = .rightToLeft
            }
            return direction
        }
    }
}

extension UILabel {
    
    public func cstmlayoutSubviews() {
        
        self.cstmlayoutSubviews()
        if self.isKind(of: NSClassFromString("UITextFieldLabel")!) {
            return // handle special case with uitextfields
        }
        if self.tag <= 0  {
            if UIApplication.isRTL()  {
                if self.textAlignment == .right {
                    return
                }
            } else {
                if self.textAlignment == .left {
                    return
                }
            }
        }
        if self.tag <= 0 {
            
            if UIApplication.isRTL()  {
                
                self.textAlignment = .right
                
            } else {
                
                self.textAlignment = .left
                
            }
        }
    }
}

extension UITextField {
    
    public func cstmlayoutSubviews() {
        
        self.cstmlayoutSubviews()
        
        if self.tag <= 0 {
            
            if UIApplication.isRTL()  {
                
                if self.textAlignment == .right { return }
                self.textAlignment = .right
                
            } else {
                
                if self.textAlignment == .left { return }
                self.textAlignment = .left

            }
        }
    }
}

extension UIApplication {
    
    class func isRTL() -> Bool{
        return UIApplication.shared.userInterfaceLayoutDirection == .rightToLeft
    }
}
