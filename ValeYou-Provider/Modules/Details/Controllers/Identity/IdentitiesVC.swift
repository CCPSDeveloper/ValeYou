//
//  IdentitiesVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class IdentitiesVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblIdentity: UITableView!{
        didSet{
            tblIdentity.registerXIB(CellIdentifiers.IdentityCell.rawValue)
        }
    }
    
    
    var dataSource:TableViewDataSource?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
    }
    
    func getData(){
      //  ProviderEP.get
    }

    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
   
    @IBAction func btnActionAdd(_ sender: Any) {
//        guard let vc = R.storyboard.details.addIdentityVC() else { return }
//        Router.shared.pushVC(vc: vc)
    }
    
    func configureTbl(){
      
     dataSource = TableViewDataSource(items: [1], tableView: tblIdentity, cellIdentifier: CellIdentifiers.IdentityCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? IdentityCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblIdentity.delegate = dataSource
           tblIdentity.dataSource = dataSource
           tblIdentity.reloadData()
    }

}
