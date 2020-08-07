//
//  PortfolioVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class PortfolioVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblPort: UITableView!{
        didSet{
            tblPort.registerXIB(CellIdentifiers.PortfolioCell.rawValue)
        }
    }
    
    var dataSource:TableViewDataSource?
    var portfolioList = [PortfolioData](){
        didSet{
            configureTbl()
        }
    }
    var shouldLoad = true
    
    
    //MARK: - LIfe Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
//        getData()
    }
    
    func getData(){
//        ProviderEP.getPortfolio.request(loader: shouldLoad , success: { (res) in
//            guard let data = res as? PortfolioModel else { return }
//            self.portfolioList = data.data ?? []
//            self.shouldLoad = false
//            self.configureTbl()
//        }) { (error) in
//            Toast.shared.showAlert(type: .apiFailure, message: /error)
//        }
    }

    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    
    @IBAction func btnActionAdd(_ sender: Any) {
//        guard let vc = R.storyboard.details.addPortfolio() else { return }
//        Router.shared.pushVC(vc: vc)
    }
    
    
    func configureTbl(){
            
           dataSource = TableViewDataSource(items: portfolioList, tableView: tblPort, cellIdentifier: CellIdentifiers.PortfolioCell.rawValue, configureCellBlock: { (cell, item, index) in
                        guard let mCell = cell as? PortfolioCell else { return }
                        mCell.item = item
                    }, aRowSelectedListener: { (index, item) in
                      
                    }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
                 tblPort.delegate = dataSource
                 tblPort.dataSource = dataSource
                 tblPort.reloadData()
          }
       
    
}