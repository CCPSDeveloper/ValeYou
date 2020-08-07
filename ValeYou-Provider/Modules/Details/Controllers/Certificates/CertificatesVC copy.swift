//
//  CertificatesVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CertificatesVC: UIViewController {
    
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblCerts: UITableView!{
        didSet{
            tblCerts.registerXIB(CellIdentifiers.CertificateCell.rawValue)
        }
    }
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    var shouldLoad = true
    var certList = [PortfolioData]()
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        getData()
    }
    
    func getData(){
        ProviderEP.getCertificates.request(loader: shouldLoad, success: { (res) in
            guard let data = res as? PortfolioModel else { return }
            self.shouldLoad = false
            self.certList = data.data ?? []
            self.configureTbl()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionAdd(_ sender: Any) {
        guard let vc = R.storyboard.details.addCertificatesVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    func configureTbl(){
        dataSource = TableViewDataSource(items:certList, tableView: tblCerts, cellIdentifier: CellIdentifiers.CertificateCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? CertificateCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblCerts.delegate = dataSource
           tblCerts.dataSource = dataSource
           tblCerts.reloadData()
    }
}
