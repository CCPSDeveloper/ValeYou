//
//  RecoveryPassVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class RecoveryPassVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    @IBAction func btnActionChangePass(_ sender: Any) {
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
}
