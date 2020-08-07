//
//  SignupBasicDetailsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift
import CoreLocation

class SignupBasicDetailsVC: UIViewController {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var tfAge: ACFloatingTextfield!
    @IBOutlet weak var tfPaypalId: ACFloatingTextfield!
    @IBOutlet weak var tfAddress: ACFloatingTextfield!
    @IBOutlet weak var imgRemember: UIImageView!
    @IBOutlet weak var isNGOSwitch: UISwitch!
    @IBOutlet weak var tfDesc: ACFloatingTextfield!
    @IBOutlet weak var tfSocialID: ACFloatingTextfield!
    var data:SignupData?
    var latitude:Double = 0.0
    var longitude:Double = 0.0
    
    //MARK: - Life Cycle Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tfAddress.delegate = self
    }
    
    func validate()->String{
        if /tfAge.text?.isEmpty || /tfSocialID.text?.isEmpty || /tfAddress.text?.isEmpty  {
            return AlertMessage.REQUIRED_EMPTY
        }
        return ""
    }
    
    
    //MARK: - IBAction Methods
    
    
    @IBAction func termsAndPolicyBtn(_ sender: Any) {
        
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionNext(_ sender: Any) {
        let valid = validate()
        if valid == ""{
            data?.age = /tfAge.text
            data?.address = /tfAddress.text
            //            data?.paypalId = /tfPaypalId.text
            data?.lat = "\(latitude)"
            data?.long = "\(longitude)"
 
            UserEP.signup(firstName: (self.data?.firstName)!, lastName: (self.data?.lastName)!, email: (self.data?.email)!, password: (self.data?.password)!, phone: (self.data?.phoneNum)!, country_code: (self.data?.countryCode)!, address: "", isNGO: (self.data?.isNGO)!, latitude: "\(self.latitude)", longitude: "\(self.longitude)", age: /tfAge.text!, socialSecurityNo: /tfSocialID.text!, selectedData: "1", deviceType: "2", deviceToken: "123456", socialId: self.data?.socialId, driverLicense: nil, image: nil, paypalId: "", description: "", socialType: self.data?.socialType ?? "").request(loader: true, success: { (data) in
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
    }
}

extension SignupBasicDetailsVC:SelectLocationDelegate{
    func locationSelected(address: String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D) {
        self.tfAddress.text = address
        self.latitude = coordinates.latitude
        self.longitude = coordinates.longitude
      }
}

 extension SignupBasicDetailsVC: UITextFieldDelegate{
    
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == tfAddress{
            let vc = SelectLocationVC(nibName: String(describing:SelectLocationVC.self), bundle: nil)
                    vc.delegate = self
                    self.present(vc, animated: true, completion: nil)
        }
    }
}
