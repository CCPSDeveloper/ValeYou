//
//  SignupCategoryVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SignupCategoryVC: UIViewController {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var tblCat: UITableView!{
        didSet{
            tblCat.registerXIB(CellIdentifiers.CategoryCell.rawValue)
        }
    }
    @IBOutlet weak var heightView: NSLayoutConstraint!{
        didSet{
            heightView.constant = UIScreen.main.bounds.size.height - 200
        }
    }
    
    var dataSource:TableViewDataSource?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTable()
    }
    
    func configureTable(){
        dataSource = TableViewDataSource(items: [1,2,3,4,5,6,7,8,9], tableView: tblCat, cellIdentifier: CellIdentifiers.CategoryCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? CategoryCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            let priceView = SetPriceView(frame: self.view.bounds)
            self.view.addSubview(priceView)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil, scrollToBottom: nil)
        tblCat.dataSource = dataSource
        tblCat.delegate = dataSource
        tblCat.reloadData()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    

}
