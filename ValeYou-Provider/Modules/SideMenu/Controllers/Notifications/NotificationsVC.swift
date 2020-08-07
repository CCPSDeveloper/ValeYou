//
//  NotificationsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//
import KYDrawerController
import UIKit

class NotificationsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var tblNotifications: UITableView!{
        didSet{
            tblNotifications.registerXIB(CellIdentifiers.NotificationCell.rawValue)
        }
    }
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
       
        configureTbl()
        getData()
    }
    
    func getData(){
        ProviderEP.getNotifications.request(loader: true, success: { (res) in
            
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    
    
    func configureTbl(){
        dataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblNotifications, cellIdentifier: CellIdentifiers.NotificationCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? NotificationCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                  guard let vc = R.storyboard.details.newJobVC() else { return }
                             Router.shared.pushVC(vc: vc)
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblNotifications.delegate = dataSource
           tblNotifications.dataSource = dataSource
           tblNotifications.reloadData()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionMenu(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
}
