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
    
    @IBOutlet weak var lblVerified: UILabel!
    @IBOutlet weak var lblAddress: UILabel!
    
    
    @IBOutlet weak var lblDesc: UILabel!
    @IBOutlet weak var lblCategory: UILabel!
    @IBOutlet weak var lblAge: UILabel!
    @IBOutlet weak var lblPhone: UILabel!
    @IBOutlet weak var lbEmail: UILabel!
    @IBOutlet weak var lblName: UILabel!
    
    
    @IBOutlet weak var imgVwProfile: UIImageView!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblOptions: UITableView!{
        didSet{
            tblOptions.registerXIB(CellIdentifiers.ProfileOptionCell.rawValue)
        }
    }
    @IBOutlet weak var heightTbl: NSLayoutConstraint!
    
    var optionArray = ["Languages".localize,"Portfolio".localize,"myService".localize,"businessHour".localize,"IdentityVerification".localize,"certificate&Award".localize,"reviews".localize,"changePassword".localize,"deactivateAccount".localize,"deleteAccount".localize]
    
    var dataSource:TableViewDataSource?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupView()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setData()
    }
    

    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
    }
    
    func setData(){
        let data = UserPreference.shared.data
        lblName.text = /data?.firstName + " " + /data?.lastName
        lbEmail.text = /data?.email
        lblPhone.text = /data?.countryCode + /data?.phone
        lblAge.text = "\(/data?.age)"
        lblAddress.text = /data?.address
       
        var categories = ""
//        for cat in data?.subCategories ?? []{
//            categories += /cat.categoryName
//            if /cat.categoryName != /data?.subCategories?.last?.categoryName{
//                categories += ","
//            }
//        }
        lblCategory.text = categories
//        lblDesc.text = /data?.description
        imgVwProfile.setImageKF(/data?.image, placeholder: #imageLiteral(resourceName: "user.png"))
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionEdit(_ sender: Any) {
        
        guard let vc = R.storyboard.details.editProfileVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    
    func configureTbl(){
        heightTbl.constant = CGFloat(50 * optionArray.count)
        dataSource = TableViewDataSource(items: optionArray, tableView: tblOptions, cellIdentifier: CellIdentifiers.ProfileOptionCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? ProfileOptionCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                self.handleSelection(index:index.row)
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblOptions.delegate = dataSource
           tblOptions.dataSource = dataSource
           tblOptions.reloadData()
    }
    
    func handleSelection(index:Int){
        switch index{
        case 0:
            guard let vc = R.storyboard.details.languageVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 1:
            guard let vc = R.storyboard.details.portfolioVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 2:
            guard let vc = R.storyboard.authentication.signupCategoryVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 3:
            guard let vc = R.storyboard.details.businessHourVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 4:
            guard let vc = R.storyboard.details.identitiesVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 5:
            guard let vc = R.storyboard.details.certificatesVC() else{ return }
            Router.shared.pushVC(vc: vc)
        case 6:
            guard let vc = R.storyboard.sideMenu.reviewsVC() else { return}
            Router.shared.pushVC(vc: vc)
        case 7:
            guard let vc = R.storyboard.authentication.changePassVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 8:
            break
        case 9:
            break
        default:
            break
        }
    }
}
