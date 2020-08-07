//
//  AddAddressVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import CoreLocation

protocol AddAddressVCDelegate {
    func didSaved(address: String,apartmentNo:String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D)
}

class AddAddressVC: UIViewController {
    
    @IBOutlet weak var appartmentNoTf: UITextField!
    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewAddress: UIView!
    @IBOutlet weak var viewApartmentNo: UIView!

    @IBOutlet weak var viewCity: UIView!
    @IBOutlet weak var viewState: UIView!
    @IBOutlet weak var viewCountry: UIView!
    
    @IBOutlet weak var addressTf: UITextField!
    @IBOutlet weak var cityTf: UITextField!
    @IBOutlet weak var stateTf: UITextField!
    @IBOutlet weak var countryTf: UITextField!
    @IBOutlet weak var zipCodeTf: UITextField!

    @IBOutlet weak var viewZip: UIView!
    var delegate : AddAddressVCDelegate?
    var data = PostData()
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
         viewAddress.addDashedBorder(width: viewAddress.frame.size.width - 35, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)
        viewApartmentNo.addDashedBorder(width: viewAddress.frame.size.width - 35, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)

         viewCity.addDashedBorder(width: viewCity.frame.size.width - 15, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)
        viewState.addDashedBorder(width: viewState.frame.size.width - 15, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)
        viewCountry.addDashedBorder(width: viewCountry.frame.size.width - 15, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)
        viewZip.addDashedBorder(width: viewZip.frame.size.width - 15, height: nil, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray,radius:8, fillColor: .clear)
         addressTf.delegate = self
     }
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionSave(_ sender: Any) {
        if addressTf.text!.isEmpty || appartmentNoTf.text!.isEmpty || cityTf.text!.isEmpty || stateTf.text!.isEmpty ||  zipCodeTf.text!.isEmpty {
            Toast.shared.showAlert(type: .validationFailure, message: "Please filling the empty fields")
        }else{

            delegate?.didSaved(address: addressTf.text!, apartmentNo: appartmentNoTf.text!, city: cityTf.text!, state: stateTf.text!, country: countryTf.text!, pincode: zipCodeTf.text!, coordinates: CLLocationCoordinate2D(latitude: data.lat!, longitude: data.long!))
            Router.shared.popFromInitialNav()
        }
        

    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        
        Router.shared.popFromInitialNav()
    }
}

extension AddAddressVC: SelectLocationDelegate{
    func locationSelected(address: String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D){
        addressTf.text = address
        cityTf.text = city
        stateTf.text = state
        countryTf.text = country
        zipCodeTf.text = pincode
       //data
        data.address = address
        data.city = city
        data.state = state
//        data.country = country
        data.zipCode = pincode
        data.lat = coordinates.latitude
        data.long = coordinates.longitude
       }
    
}

extension AddAddressVC : UITextFieldDelegate{
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == addressTf{
            let vc = SelectLocationVC(nibName: String(describing:SelectLocationVC.self), bundle: nil)
                    vc.delegate = self
                    self.present(vc, animated: true, completion: {
                        self.addressTf.endEditing(true)
                    
                    })
        }
    }
}
