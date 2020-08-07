//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
//import KVSpinnerView
import UIKit

class Utility{
    
    static let shared = Utility()
    
    
    //MARK: - Method to drop Shadow
    static func dropShadow(mView:UIView,radius:Int,color:UIColor,size:CGSize){
       // mView.layer.borderWidth = 0.5
        mView.layer.borderColor = color.cgColor
        //mView.layer.cornerRadius = CGFloat(radius)
        mView.layer.shadowColor = color.cgColor
        mView.layer.shadowOpacity = 0.7
        mView.layer.shadowOffset = size
        mView.layer.shadowRadius = CGFloat(radius)
        mView.layer.masksToBounds = false
    }
    
    static func shadow(mView:UIView,viewRadius:CGFloat? = nil, radius:CGFloat? = nil, shadowOpacity: CGFloat? = nil, color:UIColor, size:CGSize? = nil){
            // mView.layer.borderWidth = 0.5
             mView.layer.borderColor = color.cgColor
             //mView.layer.cornerRadius = CGFloat(radius)
             mView.layer.shadowColor = color.cgColor
          mView.layer.shadowOpacity = Float(shadowOpacity ?? 0.7)
             mView.layer.shadowOffset = size ?? CGSize(width: 0, height: 1)
          mView.layer.shadowRadius = radius ?? 4//
          mView.layer.cornerRadius = viewRadius ?? mView.halfHeight()
             mView.layer.masksToBounds = false
         }
    
    //MARK: - Method to add Border
    static func makeBorder(mView:UIView,radius:Int,width:CGFloat,color:UIColor){
        mView.layer.cornerRadius = CGFloat(radius)
        mView.layer.borderColor = color.cgColor
        mView.layer.borderWidth = width
        mView.layer.masksToBounds = true
    }
    
    //MARK: - Method to make view round
    static func makeViewRound(mView:UIView){
        mView.layer.cornerRadius = mView.frame.size.width/2
        mView.layer.masksToBounds = true
    }
    
    //MARK: - Method to make view round
    static func makeCornerRounds(mView:UIView,radius:CGFloat){
        mView.layer.cornerRadius = radius
        mView.layer.masksToBounds = true
    }
    
    
    //MARK: - Method to prepare alert View
    static func makeAlert(title:String,message:String)->UIAlertController{
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Ok".localize, style: .default, handler: nil)
        alert.addAction(okAction)
        return alert
    }
    
    static func setPlaceholder(textField:UITextField){
        let placeholderAttributedString = NSMutableAttributedString(attributedString: textField.attributedPlaceholder!)
        placeholderAttributedString.addAttribute(.foregroundColor, value: UIColor.white, range: NSRange(location: 0, length: placeholderAttributedString.length))
        textField.attributedPlaceholder = placeholderAttributedString
    }
    
    //MARK: - Method to check valid email Address
    static func isValidEmail(testStr:String) -> Bool {
        print("validate emilId: \(testStr)")
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        let result = emailTest.evaluate(with: testStr)
        return result
    }
    
    // MARK:- CALL
    static func callNumber(phoneNumber:String) {
        if let phoneCallURL:NSURL = NSURL(string:"tel://\(phoneNumber)") {
            let application:UIApplication = UIApplication.shared
            if (application.canOpenURL(phoneCallURL as URL)) {
                if #available(iOS 10, *){
                    application.open(phoneCallURL as URL, options: [:], completionHandler: nil)
                }else{
                application.openURL(phoneCallURL as URL)
                }
            }
        }
    }
    
    //MARK:- VISIT URL / OPEN BROWSER
   static func visitUrl(url: String){
        if let url = URL(string: url) {
            if #available(iOS 10, *){
                UIApplication.shared.open(url)
            }else{
                UIApplication.shared.openURL(url)
            }
        }
    }
    
//    static func showLoading(vc:UIViewController){
//        let loader = Loader(frame: vc.view.bounds)
//        loader.tag = 5555
//        vc.view.addSubview(loader)
//        vc.view.bringSubviewToFront(loader)
//        loader.loader.startAnimating()
//    }
//
//    static func removeLoading(vc:UIViewController){
//        for v in vc.view.subviews{
//            if v.tag == 5555{
//                v.removeFromSuperview();
//            }
//        }
//    }
    
//    func showLoader(){
//        KVSpinnerView.settings.spinnerRadius = 32
//               KVSpinnerView.settings.linesWidth = 4
//               KVSpinnerView.settings.tintColor = .white
//               KVSpinnerView.settings.backgroundOpacity = 1.0
//               KVSpinnerView.settings.backgroundRectColor = #colorLiteral(red: 0.3040754199, green: 0.3141314387, blue: 1, alpha: 1)
//        UIApplication.shared.beginIgnoringInteractionEvents()
//        KVSpinnerView.show()
//    }
//    
//    func removeLoader() {
//        UIApplication.shared.endIgnoringInteractionEvents()
//        KVSpinnerView.dismiss()
//    }
//    
    
    
    //MARK: - Method to detect valid phone number
    //    static func isValidPhone(value: String) -> Bool {
    //            let PHONE_REGEX = "^\\d{3}-\\d{3}-\\d{4}$"
    //
    //            let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
    //        let result =  phoneTest.evaluate(with: value)
    //
    //
    //        print(value.count);
    //        if value.count == 10 && result{
    //            return true
    //        }
    //        else{
    //            return false
    //        }
    //    }
    
    static func isValidPhone(value: String) -> Bool {
        let PHONE_REGEX = "[0-9]{10,12}$"//"^((\\+)|(00))[0-9]{6,14}$"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
        let result =  phoneTest.evaluate(with: value)
        return result
    }
    
    static func isNumber(value: String) -> Bool {
        let PHONE_REGEX = "[0-9]{1,12}$"//"^((\\+)|(00))[0-9]{6,14}$"
        let phoneTest = NSPredicate(format: "SELF MATCHES %@", PHONE_REGEX)
        let result =  phoneTest.evaluate(with: value)
        return result
    }
    
    static func isValidPassword(value:String)->Bool{
        let PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[d$@$!%*?&#])[A-Za-z\\dd$@$!%*?&#]{6,}"
        let passTest = NSPredicate(format: "SELF MATCHES %@", PASSWORD_REGEX)
        let result = passTest.evaluate(with: value)
        return result
    }
    
    static func isPhoneOrEmail(value:String)->String{
        if isValidPhone(value: value){
            return "phone"
        }else if isValidEmail(testStr: value){
            return "email"
        }
        return "invalid"
    }
    
    
    //MARK: - Method to check whether key present in User Defaults
    static func isKeyPresentInUserDefaults(key:String)->Bool{
        return UserDefaults.standard.object(forKey: key) != nil
    }

}
