//
//  ProfileVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 30/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class ProfileVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var imgVwProfile: UIImageView!
    @IBOutlet weak var viewBack: UIView!
    
    @IBOutlet weak var lblVerified: UILabel!
    @IBOutlet weak var lblAddress: UILabel!
    
    @IBOutlet weak var lblDesc: UILabel!
    @IBOutlet weak var lblCategory: UILabel!
    @IBOutlet weak var lblAge: UILabel!
    @IBOutlet weak var lblPhone: UILabel!
    @IBOutlet weak var lbEmail: UILabel!
    @IBOutlet weak var lblName: UILabel!
    
    @IBOutlet weak var tblOptions: UITableView!{
        didSet{
            tblOptions.registerXIB(CellIdentifiers.ProfileOptionCell.rawValue)
        }
    }
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        NotificationCenter.default.addObserver(self, selector: #selector(closeDrawer), name: Notification.Name("CloseDrawer"), object: nil)
        setupView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        setData()
    }
    
    func setData(){
        let data = UserPreference.shared.data
        lblName.text = /data?.firstName + " " + /data?.lastName
        lbEmail.text = /data?.email
        lblPhone.text = /data?.countryCode + /data?.phone
        //         lblAge.text = "\(data?.age ?? "")"
        lblAddress.text = data?.address ?? ""
        
        var categories = ""
        for cat in data?.subCategories ?? []{
            categories += /cat.categoryName
            if /cat.categoryName != /data?.subCategories?.last?.categoryName{
                categories += ","
            }
        }
        //         lblCategory.text = categories
        //         lblDesc.text = /data?.description
        if let img = data?.image{
            imgVwProfile.setImageKF(img, placeholder: #imageLiteral(resourceName: "user.png"))
        }
    }
    
    @objc func closeDrawer(){
        if let vc = self.navigationController?.parent as? KYDrawerController{
            vc.setDrawerState(.closed, animated: true)
        }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionback(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionEdit(_ sender: Any) {
        
        guard let vc = R.storyboard.details.editProfileVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
}
