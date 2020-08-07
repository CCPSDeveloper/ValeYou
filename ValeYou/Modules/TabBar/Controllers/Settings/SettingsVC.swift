//
//  SettingsVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import FBSDKLoginKit
import GoogleSignIn

class SettingsVC: UIViewController {

      //MARK: - IBOutlets
    
    
    @IBOutlet weak var tblBase: UIView!
    @IBOutlet weak var lblAddress: UILabel!
    @IBOutlet weak var imgUser: UIImageView!
    @IBOutlet weak var lblName: UILabel!
    
    @IBOutlet weak var viewAccount: UIView!
    @IBOutlet weak var tblMenu: UITableView!{
        didSet{
            tblMenu.registerXIB(CellIdentifiers.SideCell.rawValue)
        }
    }

        //MARK: - Properties
        var dataSource:TableViewDataSource?
        
    var sideArray = [["icon":#imageLiteral(resourceName: "home-sidemenu-iocn.png"),"name":"home".localize],["icon":#imageLiteral(resourceName: "job-sidemenu-iocn."),"name":"jobHistory".localize],["icon":#imageLiteral(resourceName: "reviews-sidemenu-iocn."),"name":"reviews".localize],["icon":#imageLiteral(resourceName: "chats-sidemenu-iocn."),"name":"chats".localize],["icon":#imageLiteral(resourceName: "Notification-sidemenu-iocn."),"name":"notification".localize],["icon":#imageLiteral(resourceName: "seetings-sidemenu-iocn."),"name":"settings".localize],["icon":#imageLiteral(resourceName: "logout-sidemenu-iocn."),"name":"logout".localize]]

    var account = [["icon":#imageLiteral(resourceName: "social"),"name":"profile".localize],["icon":#imageLiteral(resourceName: "change-icon-1"),"name":"changePassword".localize],["icon":#imageLiteral(resourceName: "privacy-sidemenu"),"name":"terms".localize],["icon":#imageLiteral(resourceName: "aboutus-icon"),"name":"aboutUs".localize],["icon":#imageLiteral(resourceName: "Help-icon"),"name":"helpSupport".localize],["icon":#imageLiteral(resourceName: "logout-sidemenu-iocn."),"name":"logout".localize]]
        
        var isAccount = false
        
         //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        tblMenu.tableFooterView = UIView()
        //        viewAccount.isHidden = true
        configureTbl()
        DispatchQueue.main.async{
            self.tblBase.roundCorners([.topRight,.topLeft], radius: 18)
            if let imgBase = self.imgUser.superview{
                imgBase.addDashedCircle(strokeColor: .white,lineDashPattern: [2,3])
            }
        }
    }
        
        //MARK: - IBAction Methods
        
    @IBAction func btnActionBack(_ sender: Any) {
        viewAccount.isHidden = true
        dataSource?.items = sideArray
        isAccount = false
        tblMenu.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        let data = UserPreference.shared.data
        lblName.text = /data?.firstName + " " + /data?.lastName
        lblAddress.text = /data?.address
        imgUser.setImageKF(APIConstant.mediaBasePath + /data?.image, placeholder: #imageLiteral(resourceName: "user.png"))
    }
 
    func configureTbl(){
        dataSource = TableViewDataSource(items: account, tableView: tblMenu, cellIdentifier: CellIdentifiers.SideCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? SideCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            Router.shared.drawer?.setDrawerState(.closed, animated: false)
            self.setFrontVC(index: index.row)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil, scrollToBottom: nil)
        tblMenu.dataSource = dataSource
        tblMenu.delegate = dataSource
        tblMenu.reloadData()
    }
        
    func setFrontVC(index:Int){
        
        
        switch index{
        case 0:
            
            guard let vc = R.storyboard.details.profileVC() else { return }
            Router.shared.pushVC(vc: vc)
            
        case 1:
            
            guard let vc = R.storyboard.authentication.changePassVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 2:
            
            guard let vc = R.storyboard.helpSupport.termsVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 3:
            
            guard let vc = R.storyboard.helpSupport.aboutUsVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 4:
            
            guard let vc = R.storyboard.helpSupport.helpVC() else { return }
            Router.shared.pushVC(vc: vc)
        case 5:
            signOut()
            break
            
        default:
            break
        }
    }
    
    func signOut(){
        UserEP.logout.request(loader: true, success: { (res) in
            UserPreference.shared.setLoggedIn(false)
            GIDSignIn.sharedInstance()?.signOut()
            Profile.current = nil
            Router.shared.setLoginAsRoot()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
}
