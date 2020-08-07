//
//  BusinessHourVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class BusinessHourVC: UIViewController {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var lblMondayTime: UILabel!
    @IBOutlet weak var lblTuesdayTime: UILabel!
    @IBOutlet weak var lblWednesdayTime: UILabel!
    @IBOutlet weak var lblThursdayTime: UILabel!
    @IBOutlet weak var lblFridayTime: UILabel!
    @IBOutlet weak var lblSaturdayTime: UILabel!
    @IBOutlet weak var lblSundayTime: UILabel!
    
    @IBOutlet weak var view1: UIView!
    @IBOutlet weak var view2: UIView!
    @IBOutlet weak var view3: UIView!
    @IBOutlet weak var view4: UIView!
    @IBOutlet weak var view5: UIView!
    @IBOutlet weak var view6: UIView!
    @IBOutlet weak var view7: UIView!
    

    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view1, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view2, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: view3, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view4, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view5, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view6, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: view7, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionback(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionEdit(_ sender: Any) {
//        guard let vc = R.storyboard.details.editBusinessHourVC() else { return }
//        Router.shared.pushVC(vc: vc)
    }
    

}
