//
//  AddReviewVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 30/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class AddReviewVC: UIViewController {

  
    
    @IBOutlet weak var viewBack: UIView!
    
    override func viewDidLoad() {
          super.viewDidLoad()

         setupView()
                 
             }
             
             func setupView(){
               
                 Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
             }
             
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    

}
