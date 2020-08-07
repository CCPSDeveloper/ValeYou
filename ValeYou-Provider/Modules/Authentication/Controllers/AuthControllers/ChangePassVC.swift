//
//  ChangePassVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift
import KYDrawerController

class ChangePassVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var tfOldPass: ACFloatingTextfield!
    
    @IBOutlet weak var tfNewPass: ACFloatingTextfield!
    
    @IBOutlet weak var tfConfirmPass: ACFloatingTextfield!
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
       
    }
    
   
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionChange(_ sender: Any) {
        let valid = validation()
        if valid == ""{
            ProviderEP.changePassword(oldPassword: /tfOldPass.text, newPassword: /tfNewPass.text).request(loader: true, success: { (res) in
                Router.shared.popFromInitialNav()
            }) { (error) in
                Toast.shared.showAlert(type: .apiFailure, message: /error)
            }
        }
    }
    
    func validation()->String{
        if /tfOldPass.text?.isEmpty || /tfNewPass.text?.isEmpty || /tfConfirmPass.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }else if /tfNewPass.text != /tfConfirmPass.text{
            return AlertMessage.PASSWORD_NOT_MATCH        }
        return ""
    }
    
}
