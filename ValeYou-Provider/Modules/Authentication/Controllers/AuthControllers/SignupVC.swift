//
//  SignupVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift

class SignupVC: UIViewController {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var tfLast: ACFloatingTextfield!
    @IBOutlet weak var tfFirst: ACFloatingTextfield!
    
    @IBOutlet weak var tfEmail: ACFloatingTextfield!
    
    @IBOutlet weak var tfPhone: ACFloatingTextfield!
    @IBOutlet weak var tfPass: ACFloatingTextfield!
    @IBOutlet weak var tfConfirm: ACFloatingTextfield!
    @IBOutlet weak var tfCode: ACFloatingTextfield!
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    
    @IBAction func btnActionNext(_ sender: Any) {
        let valid = validate()
        if valid == ""{
            let data = SignupData(firstName: /tfFirst.text, lastName: /tfLast.text, email: /tfEmail.text, password: /tfPass.text,countryCode: /tfCode.text,phoneNum: /tfPhone.text, address: "", lat: "", long: "", age: "", securityCode:"" , paypalId: "")
           guard let vc = R.storyboard.authentication.signupBasicDetailsVC() else {return}
            vc.data = data
            Router.shared.pushVC(vc: vc)
        }else{
            Toast.shared.showAlert(type: .validationFailure, message: valid)
        }
        
    }
    
    
    @IBAction func btnActionCode(_ sender: Any) {
        self.view.endEditing(true)
                
        guard let vc = R.storyboard.authentication.countryCodeSearchViewController() else { return }
        vc.isRTL = L102Language.isRTL
        vc.delegate = self
        
        self.present(vc, animated: true, completion: nil)
    }
    
    func validate()->String{
        if /tfCode.text?.isEmpty || /tfFirst.text?.isEmpty || /tfLast.text?.isEmpty || /tfEmail.text?.isEmpty || /tfPass.text?.isEmpty || /tfConfirm.text?.isEmpty || /tfPhone.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }else if !Utility.isValidEmail(testStr: /tfEmail.text){
            return AlertMessage.INVALID_EMAIL
        }else if !Utility.isValidPhone(value: /tfPhone.text){
            return AlertMessage.INVALID_PHONE
        }else if /tfPass.text != /tfConfirm.text{
            return AlertMessage.PASSWORD_NOT_MATCH
        }
        return ""
    }
    
    
}

////MARK: - Country Picker Delegates
extension SignupVC: CountryCodeSearchDelegate {
    
    func didTap(onCode detail: [AnyHashable : Any]!) {
        self.tfCode.text =  (detail["dial_code"] as! String)
        
    }
}


struct SignupData{
    var firstName:String?
    var lastName:String?
    var email:String?
    var password:String?
    var countryCode:String?
    var phoneNum:String?
    var address:String?
    var lat:String?
    var long:String?
    var age:String?
    var securityCode:String?
    var paypalId:String?
    var description:String?
    var selectedData:String?
}
