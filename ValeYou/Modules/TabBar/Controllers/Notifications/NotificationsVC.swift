//
//  NotificationsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//
import KYDrawerController
import UIKit

enum NotificationType : Int{
    case favourite = 0
    case bid = 1
    case rating = 2
    case creaeNewOrder = 3
    //    User Push type :---------------------
    case startJob = 4
    case endJob = 5
    case bidOnJob = 6
}


class NotificationsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var tblNotifications: UITableView!{
        didSet{
            tblNotifications.registerXIB(CellIdentifiers.NotificationCell.rawValue)
        }
    }

    //MARK: - Properties
    var dataSource:TableViewDataSource?
    var notifications = [GetNotificationsData]()
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTbl()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        getNotifications()
     }
    
    func getNotifications(){
        UserEP.getNotifications.request(loader: true, success: { (res) in
            guard let data = res as? GetNotifications else { return }
            self.notifications = data.data
            print("notifications count: \(self.notifications.count)")
            self.configureTbl()
        }) { (error) in
            if let error = error{
                Toast.shared.showAlert(type: .apiFailure, message: error)
            }
        }
    }
    
    func configureTbl(){
        dataSource = TableViewDataSource(items: notifications, tableView: tblNotifications, cellIdentifier: CellIdentifiers.NotificationCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? NotificationCell else { return }
            mCell.item = item
            
        }, aRowSelectedListener: { (index, item) in
            guard let item = item as? GetNotificationsData else { return }
            let notificationType = NotificationType(rawValue: item.type)
            if let type = notificationType{
                if type == .bidOnJob{
                    guard let vc = R.storyboard.details.jobDetailsVC() else { return }
                    vc.jobId = item.id
                    Router.shared.pushVC(vc: vc)
                }
            }
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
