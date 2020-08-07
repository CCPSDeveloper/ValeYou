//
//  ChatVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 04/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class ChatVC: UIViewController {

    @IBOutlet weak var imgTopMargin: NSLayoutConstraint!
    
    @IBOutlet weak var heightBottom: NSLayoutConstraint!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
        if Device.IS_IPHONE_X{
            imgTopMargin.constant = -10
            heightBottom.constant = 100
        }else{
            imgTopMargin.constant = -30
            heightBottom.constant = 50
        }
    }
    
  
    

    @IBAction func btnActionBack(_ sender: Any) {
        
        Router.shared.popFromInitialNav()
    }
    

}
