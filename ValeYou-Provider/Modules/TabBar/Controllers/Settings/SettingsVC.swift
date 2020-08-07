//
//  SettingsVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SettingsVC: UIViewController {

      //MARK: - IBOutlets
    
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
        
           var sideArray = [["icon":#imageLiteral(resourceName: "social-1"),"name":"myAccount".localize],["icon":#imageLiteral(resourceName: "add-card-icon"),"name":"paymentMethod".localize],["icon":#imageLiteral(resourceName: "privacy-sidemenu"),"name":"terms".localize],["icon":#imageLiteral(resourceName: "aboutus-icon"),"name":"aboutUs".localize],["icon":#imageLiteral(resourceName: "Help-icon"),"name":"helpSupport".localize],["icon":#imageLiteral(resourceName: "logout-sidemenu-iocn."),"name":"logout".localize]]

//          var account = [["icon":#imageLiteral(resourceName: "social"),"name":"profile".localize],["icon":#imageLiteral(resourceName: "change-icon-1"),"name":"changePassword".localize],]
        
        var isAccount = false
    
    
        
        
        //MARK: - Life Cycle Methods
        override func viewDidLoad() {
            super.viewDidLoad()
            tblMenu.tableFooterView = UIView()
            viewAccount.isHidden = true
            configureTbl()
            let data = UserPreference.shared.data
            lblName.text = /data?.firstName + " " + /data?.lastName
            lblAddress.text = /data?.address
            imgUser.setImageKF(APIConstant.mediaBasePath + /data?.image, placeholder: #imageLiteral(resourceName: "user.png"))
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
            
        
                switch index{
                       case 0:
                           
                           guard let vc = R.storyboard.details.profileVC() else { return }
                           Router.shared.pushVC(vc: vc)
                           
                       case 1:
                           break

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
                       case 6:
                            break
                    
                       default:
                           break
                       }
            }
    
    func signOut(){
        ProviderEP.logout(id:/UserPreference.shared.data?.id).request(loader: true, success: { (res) in
            UserPreference.shared.setLoggedIn(false)
            Router.shared.setLoginAsRoot()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
}
