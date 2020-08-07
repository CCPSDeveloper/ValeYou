//
//  EditProfileVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 30/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import CoreLocation

class EditProfileVC: UIViewController {
    
   //MARK: - IBOutlets
        @IBOutlet weak var imgProfile: UIImageView!
        @IBOutlet weak var tfFirstName: UITextField!
        @IBOutlet weak var tfLastName: UITextField!
        @IBOutlet weak var tfEmail: UITextField!
        @IBOutlet weak var tfPhone: UITextField!
        @IBOutlet weak var viewBack: UIView!
        @IBOutlet weak var tfAge: UITextField!
        @IBOutlet weak var tfAddress: UITextField!
        @IBOutlet weak var tvDescription: UITextView!
        
    @IBOutlet weak var lblBio: UILabel!
    
        @IBOutlet weak var viewDesc: UIView!
        var selectedPicture:UIImage?
        var latitude:Double = 0.0
        var longitude:Double = 0.0
        
        //MARK: - Life Cycle Methods
        override func viewDidLoad() {
            super.viewDidLoad()
            
            setupView()
            setData()
            
        }
        
        func setData(){
            let data = UserPreference.shared.data
            tfFirstName.text = /data?.firstName
            tfLastName.text =  /data?.lastName
            tfEmail.text = /data?.email
            tfPhone.text = /data?.phone
            tfAge.text = "\(/data?.age)"
            tfAddress.text = /data?.address
            
            var categories = ""
            for cat in data?.subCategories ?? []{
                categories += /cat.categoryName
                if /cat.categoryName != /data?.subCategories?.last?.categoryName{
                    categories += ","
                }
            }
            
//            tvDescription.text = /data?.description
            imgProfile.setImageKF(/data?.image, placeholder: #imageLiteral(resourceName: "user.png"))
        }
        
        
        func setupView(){
            Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
//            Utility.dropShadow(mView: viewDesc, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        }
        
        
        
        //MARK: - IBAction Methods
        @IBAction func btnActionBack(_ sender: Any) {
            Router.shared.popFromInitialNav()
        }
        
        
        @IBAction func btnActionAddress(_ sender: Any) {
            let vc = SelectLocationVC(nibName: String(describing:SelectLocationVC.self), bundle: nil)
            vc.delegate = self
            self.present(vc, animated: true, completion: nil)
        }
        
        @IBAction func btnActionChangephoto(_ sender: Any) {
            DispatchQueue.main.async {
                AttachmentHandler.shared.showAttachmentActionSheet(vc: self)
                AttachmentHandler.shared.imagePickedBlock = { [weak self] item in
                    self?.selectedPicture = item
                    self?.imgProfile.image = item
                    
                    
                }
            }
            
        }
        
        @IBAction func btnActionSave(_ sender: Any) {
            let valid = validation()
            if valid == ""{
                UserEP.editProfile(firstName: /tfFirstName.text, lastName: /tfLastName.text, email: /tfEmail.text, phone: /tfPhone.text, countryCode: /UserPreference.shared.data?.countryCode, description: /tvDescription.text , image: selectedPicture, businessLicense: nil, insurance: nil, resume: nil, driverLicense: nil).request(loader:true,success:{ res in
                    Router.shared.popFromInitialNav()
                },error:{
                    (error) in
                    Toast.shared.showAlert(type: .apiFailure, message: /error)
                })
            }else{
                Toast.shared.showAlert(type: .validationFailure, message: valid)
            }
        }
        
        func validation() ->String{
            if /tfFirstName.text?.isEmpty || /tfLastName.text?.isEmpty || /tfAddress.text?.isEmpty || /tfPhone.text?.isEmpty || /tfAge.text?.isEmpty || /tfEmail.text?.isEmpty || /tvDescription.text == ""{
                return AlertMessage.REQUIRED_EMPTY
            }
            return ""
        }
        
    }


    extension EditProfileVC:SelectLocationDelegate{
        func locationSelected(address: String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D) {
            self.tfAddress.text = address
            self.latitude = coordinates.latitude
            self.longitude = coordinates.longitude
        }
    }
    
 
