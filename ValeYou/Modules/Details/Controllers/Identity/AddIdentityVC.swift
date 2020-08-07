//
//  AddIdentityVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class AddIdentityVC: UIViewController {
    
    //MARK: - IBAction Methods
    @IBOutlet weak var viewPic: UIView!
    @IBOutlet weak var viewBack: UIView!
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        viewPic.addDashedBorder(width: UIScreen.main.bounds.size.width - 140, height: 170, lineWidth: 1, lineDashPattern: [2,3], strokeColor: .darkGray, radius: viewPic.frame.size.height/2 , fillColor: .clear)
          Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    

    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    @IBAction func btnActionTakePic(_ sender: Any) {
    }
    @IBAction func btnActionSave(_ sender: Any) {
    }
    
    @IBAction func btnActionCancel(_ sender: Any) {
    }
    
    
}
