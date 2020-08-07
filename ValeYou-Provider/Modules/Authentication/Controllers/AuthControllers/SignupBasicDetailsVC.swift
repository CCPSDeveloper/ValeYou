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
    @IBOutlet weak var tfDesc: ACFloatingTextfield!
    
    
    var data:SignupData?
    var latitude:Double = 0.0
    var longitude:Double = 0.0
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    func validate()->String{
        if /tfAge.text?.isEmpty || /tfPaypalId.text?.isEmpty || /tfAddress.text?.isEmpty || /tfDesc.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
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
            data?.age = /tfAge.text
            data?.address = /tfAddress.text
            data?.paypalId = /tfPaypalId.text
            data?.lat = "\(latitude)"
            data?.long = "\(longitude)"
            data?.description = /tfDesc.text
            
            guard let vc = R.storyboard.authentication.signupCategoryVC() else { return }
            vc.data = data
            Router.shared.pushVC(vc: vc)
        }else{
            Toast.shared.showAlert(type: .validationFailure, message: valid)
        }
        
    }
    
    @IBAction func btnAccept(_ sender: Any) {
        
    }
    
    @IBAction func btnActionAddress(_ sender: Any) {
        let vc = SelectLocationVC(nibName: String(describing:SelectLocationVC.self), bundle: nil)
              vc.delegate = self
              self.present(vc, animated: true, completion: nil)
    }
}

extension SignupBasicDetailsVC:SelectLocationDelegate{
    func locationSelected(address: String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D) {
        self.tfAddress.text = address
        self.latitude = coordinates.latitude
        self.longitude = coordinates.longitude
      }
}
