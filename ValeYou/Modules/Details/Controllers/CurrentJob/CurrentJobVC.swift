//
//  CurrentJobVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CurrentJobVC: UIViewController {

     //MARK: - IBOutlets
    @IBOutlet weak var viewTop: UIView!
    
      //MARK: - Life Cycle Methods
        override func viewDidLoad() {
            super.viewDidLoad()
            setupView()
        }
        
        func setupView(){
           Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
             
        }
     
     
     //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionComplete(_ sender: Any) {
    }
}
