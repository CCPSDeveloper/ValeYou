//
//  SignupVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift
import RSSelectionMenu

class SignupVC: UIViewController {
    
    //MARK: - IBOutlets

    @IBOutlet weak var tfLast: ACFloatingTextfield!
    @IBOutlet weak var tfFirst: ACFloatingTextfield!
    @IBOutlet weak var tfEmail: ACFloatingTextfield!
    @IBOutlet weak var tfPhone: ACFloatingTextfield!
    @IBOutlet weak var tfCode: ACFloatingTextfield!
    @IBOutlet weak var tfIsNGO: ACFloatingTextfield!
    @IBOutlet weak var tfPass: ACFloatingTextfield!
    @IBOutlet weak var tfConfirm: ACFloatingTextfield!
    var latitude:Double = 0.0
    var longitude:Double = 0.0
    var ngoAlert : UIAlertController?
    var accepetedTerms = false{
        didSet{
            termsIcon.image = #imageLiteral(resourceName: "tick-icon-1").withTintColor(accepetedTerms ? R.color.primaryColor()! : .lightGray, renderingMode: .alwaysOriginal)
        }
    }
    @IBOutlet weak var termsIcon: UIImageView!
    var signUpData = SignupData()
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        tfIsNGO.delegate = self
        //        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(didRecognizeTapGesture(_:)))
        ////        Utility.shared
        //        tfIsNGO.superview?.addGestureRecognizer(tapGesture)
        tfIsNGO.inputAccessoryView = UIView()
        tfIsNGO.inputView = UIView()
        if self.signUpData.socialType != nil{
            self.tfPass.isHidden = true
            self.tfConfirm.isHidden = true
            
            self.tfFirst.text = /signUpData.firstName
            self.tfEmail.text = /signUpData.email
            
        }
        
    }
    
    
    //    @objc private dynamic func didRecognizeTapGesture(_ gesture: UITapGestureRecognizer) {
    //        let point = gesture.location(in: gesture.view)
    //
    //        guard gesture.state == .ended, self.tfIsNGO.frame.contains(point) else { return }
    //        showAlertSheet()
    //        //doSomething()
    //    }
    
    func validate()->String {
        
        if   /tfFirst.text?.isEmpty || /tfLast.text?.isEmpty || /tfEmail.text?.isEmpty ||  /tfPhone.text?.isEmpty || /tfCode.text?.isEmpty || /tfIsNGO.text?.isEmpty {
            return AlertMessage.REQUIRED_EMPTY
        }else if !Utility.isValidEmail(testStr: /tfEmail.text){
            return AlertMessage.INVALID_EMAIL
        }else if !Utility.isValidPhone(value: /tfPhone.text){
            return AlertMessage.INVALID_PHONE
        }else if /tfPass.text != /tfConfirm.text{
            return AlertMessage.PASSWORD_NOT_MATCH
        }else if accepetedTerms == false{
            return AlertMessage.ACCEPT_TERMS
        }else if self.signUpData.socialType == nil {
            if /tfPass.text?.isEmpty || /tfConfirm.text?.isEmpty{
                return AlertMessage.REQUIRED_EMPTY
            }
        }
        return ""
        
    }
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    
    @IBAction func btnActionNext(_ sender: Any) {
        
        let valid = validate()
        if valid == ""{
            let data = SignupData(firstName: /tfFirst.text,lastName:/tfLast.text, email: /tfEmail.text,isNGO:/tfIsNGO.text, password: /tfPass.text,countryCode: /tfCode.text,phoneNum: /tfPhone.text, address: "", lat: "", long: "", age: "", securityCode:"" , paypalId: "")
            
            UserEP.signup(firstName: (data.firstName)!, lastName: (data.lastName)!, email: (data.email)!, password: (data.password)!, phone: (data.phoneNum)!, country_code: (data.countryCode)!, address: "", isNGO: /tfIsNGO.text == "Yes" ? "1": "0", latitude: "", longitude: "", age: "", socialSecurityNo: "", selectedData: "1", deviceType: "2", deviceToken: "123456", socialId:self.signUpData.socialId, driverLicense: nil, image: nil, paypalId: "", description: "", socialType: self.signUpData.socialType ?? "").request(loader: true, success: { (data) in
                if let data = data as? Login{
                    UserPreference.shared.data = data.data
                    UserPreference.shared.setLoggedIn(true)
                    Router.shared.setDrawer()
                }
            }) { (error) in
                guard let error = error else { return }
                Toast.shared.showAlert(type: .apiFailure, message: error)
            }
        }else{
            Toast.shared.showAlert(type: .validationFailure, message: valid)
        }
        //            guard let vc = R.storyboard.authentication.signupBasicDetailsVC() else {return}
        //            vc.data = data
        //            Router.shared.pushVC(vc: vc)
        //        }else{
        //            Toast.shared.showAlert(type: .validationFailure, message: valid)
        //        }
        
        //        Router.shared.popFromInitialNav()
        //        guard let vc = R.storyboard.authentication.signupBasicDetailsVC() else {return}
        //        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func termsBtn(_ sender: Any) {
        Utility.visitUrl(url: "https://www.apple.com")
    }
    
    @IBAction func acceptTerms(_ sender: Any) {
        self.accepetedTerms = !accepetedTerms
    }
    
    @IBAction func btnActionCode(_ sender: Any) {
        self.view.endEditing(true)
        guard let vc = R.storyboard.authentication.countryCodeSearchViewController() else { return }
        vc.isRTL = L102Language.isRTL
        vc.delegate = self
        self.present(vc, animated: true, completion: nil)
    }
    
    @IBAction func btnActionIsNGO(_ sender: Any) {
        showAlertSheet()
    }
    
    func showAlertSheet(){
        DispatchQueue.main.async{
            self.ngoAlert = UIAlertController(title: "Are You A NGO?", message: "", preferredStyle: .actionSheet)
            let action1 = UIAlertAction(title: "Yes", style: .default) { [weak self] (action:UIAlertAction) in
                self?.tfIsNGO.text = "Yes"
            }
            let action2 = UIAlertAction(title: "No", style: .default) { [weak self] (action:UIAlertAction) in
                self?.tfIsNGO.text = "No"
                
            }
            let action3 = UIAlertAction(title: "Cancel", style: .cancel) { [weak self] (action:UIAlertAction) in
                print("You've pressed the destructive");
            }
            self.ngoAlert?.addAction(action1)
            self.ngoAlert?.addAction(action2)
            self.ngoAlert?.addAction(action3)
            self.present((self.ngoAlert)!, animated: true, completion: nil)
        }
    }
    
}

////MARK: - Country Picker Delegates
extension SignupVC: CountryCodeSearchDelegate {
    
    func didTap(onCode detail: [AnyHashable : Any]!) {
        self.tfCode.text =  (detail["dial_code"] as! String)
    }
}

extension SignupVC: UITextFieldDelegate{
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == self.tfIsNGO{
            showAlertSheet()
        }else{
            self.tfIsNGO.endEditing(true)
            ngoAlert = nil
        }
    }
    
}

struct SignupData{
    var firstName:String?
    var lastName:String?
    var email:String?
    var isNGO: String?
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
    var socialId: String?
    var socialType: String?
}
