//
//  InboxVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 28/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class InboxVC: UIViewController {

     //MARK: - IBOutlets
      @IBOutlet weak var tblInbox: UITableView!{
          didSet{
              tblInbox.registerXIB(CellIdentifiers.InboxCell.rawValue)
          }
      }
    
    @IBOutlet weak var viewSearch: UIView!
    
    
      
      //MARK: - Properties
      var dataSource:TableViewDataSource?
      
      
      //MARK: - Life Cycle Methods
      override func viewDidLoad() {
          super.viewDidLoad()
          setupView()
          configureTbl()
      }
    
    func setupView(){
        Utility.dropShadow(mView: viewSearch, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
      
    
      
      func configureTbl(){
          dataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblInbox, cellIdentifier: CellIdentifiers.InboxCell.rawValue, configureCellBlock: { (cell, item, index) in
                    guard let mCell = cell as? InboxCell else { return }
                    mCell.item = item
                }, aRowSelectedListener: { (index, item) in
                    guard let vc = R.storyboard.helpSupport.chatVC() else { return }
                Router.shared.pushVC(vc: vc)
                }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
             tblInbox.delegate = dataSource
             tblInbox.dataSource = dataSource
             tblInbox.reloadData()
      }
      
    

}
