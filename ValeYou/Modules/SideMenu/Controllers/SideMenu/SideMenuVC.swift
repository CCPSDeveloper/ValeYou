//
//  SideMenuVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class SideMenuVC: UIViewController {
   
    
    //MARK: - IBOutlets
   
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
        viewAccount.isHidden = true
        configureTbl()
    }
    
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        viewAccount.isHidden = true
        dataSource?.items = sideArray
        isAccount = false
        tblMenu.reloadData()
     }
 
    func configureTbl(){
        dataSource = TableViewDataSource(items: sideArray, tableView: tblMenu, cellIdentifier: CellIdentifiers.SideCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? SideCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            self.setFrontVC(index: index.row)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil, scrollToBottom: nil)
        tblMenu.dataSource = dataSource
        tblMenu.delegate = dataSource
        tblMenu.reloadData()
    }
    
    func setFrontVC(index:Int){
        
        if !isAccount{
            switch index{
                   case 0:
                       fireCloseNotification()
//                       guard let vc = R.storyboard.sideMenu.homeVC() else { return }
//                       Router.shared.changeMainVC(vc: vc)
                       
                   case 1:
                       fireCloseNotification()
                       guard let vc = R.storyboard.sideMenu.historyVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                   case 2:
                       fireCloseNotification()
                       guard let vc = R.storyboard.sideMenu.reviewsVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                   case 3:
                       fireCloseNotification()
//                       guard let vc = R.storyboard.sideMenu.inboxVC() else { return }
//                       Router.shared.changeMainVC(vc: vc)
                   case 4:
                       fireCloseNotification()
                       guard let vc = R.storyboard.sideMenu.notificationsVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                   case 5:
                       isAccount = true
                       dataSource?.items = account
                       tblMenu.reloadData()
                       viewAccount.isHidden = false
                   default:
                       break
                   }
        }else{
            switch index{
                   case 0:
                       fireCloseNotification()
                       guard let vc = R.storyboard.details.profileVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                       
                   case 1:
                       fireCloseNotification()
                       guard let vc = R.storyboard.authentication.changePassVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                   case 2:
                       fireCloseNotification()
                       guard let vc = R.storyboard.helpSupport.termsVC() else { return }
                       Router.shared.changeMainVC(vc: vc)
                   case 3:
                   fireCloseNotification()
                   guard let vc = R.storyboard.helpSupport.aboutUsVC() else { return }
                   Router.shared.changeMainVC(vc: vc)
                   case 4:
                     fireCloseNotification()
                     guard let vc = R.storyboard.helpSupport.helpVC() else { return }
                     Router.shared.changeMainVC(vc: vc)
                   case 5:
                        break
                
                   default:
                       break
                   }
        }
       
        

    }
    
    func fireCloseNotification(){
        NotificationCenter.default.post(name: Notification.Name("CloseDrawer"), object: nil)
    }
}
