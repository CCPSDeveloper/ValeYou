//
//  ForgotPassVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift
class ForgotPassVC: UIViewController {

    //MARK: - IBOutlets
    
    @IBOutlet weak var tfEmail: ACFloatingTextfield!
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        
    }
    

    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionNext(_ sender: Any) {
        
        if /tfEmail.text?.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: AlertMessage.EMPTY_EMAIL)
        }else{
            ProviderEP.forgotPassword(email: /tfEmail.text).request(loader: true, success: { (res) in
                self.showOkAlert(message: "New Password has been sent to your email.", title: "ValeYou", actionTitle: "Ok".localize) {
                    Router.shared.popFromInitialNav()
                }
//                guard let vc = R.storyboard.authentication.recoveryPassVC() else { return }
//                Router.shared.pushVC(vc: vc)
            }) { (error) in
                Toast.shared.showAlert(type: .apiFailure, message: /error)
            }
        }
        
    }
    
}
