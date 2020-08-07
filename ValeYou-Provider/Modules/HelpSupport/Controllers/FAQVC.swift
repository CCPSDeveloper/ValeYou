//
//  FAQVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FAQVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var tblFaq: UITableView!{
        didSet{
            tblFaq.registerXIB(CellIdentifiers.FAQCell.rawValue)
        }
    }
    
    @IBOutlet weak var viewBack: UIView!
    
    var dataSource:TableViewDataSource?
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
        // Do any additional setup after loading the view.
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }

    func configureTbl(){
              dataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblFaq, cellIdentifier: CellIdentifiers.FAQCell.rawValue, configureCellBlock: { (cell, item, index) in
                        guard let mCell = cell as? FAQCell else { return }
                        mCell.item = item
                    }, aRowSelectedListener: { (index, item) in
                        
                    }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
                 tblFaq.delegate = dataSource
                 tblFaq.dataSource = dataSource
                 tblFaq.reloadData()
          }
}
