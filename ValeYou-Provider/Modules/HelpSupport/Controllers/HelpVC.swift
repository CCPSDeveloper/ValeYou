//
//  HelpVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class HelpVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewChat: UIView!
    
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewEmail: UIView!
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewChat, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewEmail, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
   
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionFaq(_ sender: Any) {
        guard let vc = R.storyboard.helpSupport.faqvC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    
    @IBAction func btnActionEmail(_ sender: Any) {
        
    }
}
