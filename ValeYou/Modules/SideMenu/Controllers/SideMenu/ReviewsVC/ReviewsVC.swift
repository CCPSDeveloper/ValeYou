//
//  ReviewsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class ReviewsVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var tblReviews: UITableView!{
        didSet{
            tblReviews.registerXIB(CellIdentifiers.ReviewCell.rawValue)
        }
    }
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTbl()
        NotificationCenter.default.addObserver(self, selector: #selector(closeDrawer), name: Notification.Name("CloseDrawer"), object: nil)
        
    }
    
    @objc func closeDrawer(){
        if let vc = self.navigationController?.parent as? KYDrawerController{
            vc.setDrawerState(.closed, animated: true)
        }
    }
    
    func configureTbl(){
        dataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblReviews, cellIdentifier: CellIdentifiers.ReviewCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? ReviewCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                  
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblReviews.delegate = dataSource
           tblReviews.dataSource = dataSource
           tblReviews.reloadData()
    }
    
     //MARK: - IBAction Methods
    @IBAction func btnActionMenu(_ sender: Any) {
        if let vc = self.navigationController?.parent as? KYDrawerController{
                vc.setDrawerState(.opened, animated: true)
        }
    }
    
   
    @IBAction func btnActionAdd(_ sender: Any) {
    guard let vc = R.storyboard.helpSupport.addReviewVC() else { return }
                          Router.shared.pushVC(vc: vc)
    }
    
}
