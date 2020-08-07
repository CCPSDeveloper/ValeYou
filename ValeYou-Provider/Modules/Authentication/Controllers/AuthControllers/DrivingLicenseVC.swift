//
//  DrivingLicenseVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 22/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class DrivingLicenseVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewSecurity: UIView!
    @IBOutlet weak var tfSecurityNumber: UITextField!
    
    @IBOutlet weak var imgVwLicense: UIImageView!
    
    
    var data:SignupData?
    var selectedPicture:UIImage?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        imgVwLicense.isHidden = true
        
        Utility.dropShadow(mView: viewSecurity, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    //MARK: - IBAction Method
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionUpload(_ sender: Any) {
        DispatchQueue.main.async {
                    AttachmentHandler.shared.showAttachmentActionSheet(vc: self)
                    AttachmentHandler.shared.imagePickedBlock = { [weak self] item in
                    self?.selectedPicture = item
                    self?.imgVwLicense.isHidden = false
                    self?.imgVwLicense.image = item
                             
                }
            }
    }
    
    @IBAction func btnActionSubmit(_ sender: Any) {
        let valid = validate()
        if valid == ""{
            ProviderEP.signup(firstName: /self.data?.firstName, lastName: /self.data?.lastName, email: /self.data?.email, password: /self.data?.password, phone: /self.data?.phoneNum, country_code: /self.data?.countryCode, address: /self.data?.address, latitude: /self.data?.lat, longitude: /self.data?.long, age: /self.data?.age, socialSecurityNo: /self.tfSecurityNumber.text, selectedData: /self.data?.selectedData, deviceType: "2", deviceToken: "123456", socialId: nil, driverLicense: self.selectedPicture, image: nil, paypalId: /self.data?.paypalId,description: /self.data?.description).request(loader: true, success: { (res) in
                            guard let data = res as? Login else { return }
                            UserPreference.shared.data = data.data
                            UserPreference.shared.setLoggedIn(true)
                            Router.shared.setDrawer()
                        }) { (error) in
                            Toast.shared.showAlert(type: .apiFailure, message: /error)
                        }
            
        }else{
            Toast.shared.showAlert(type: .validationFailure, message: valid)
        }
        
    }
    
    func validate() -> String{
        if /tfSecurityNumber.text?.isEmpty{
            return AlertMessage.EMPTY_SECURITY_NUMBER
        }else{
            return ""
        }
    }
    
    
}
