//
//  LanguageVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class LanguageVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    
    @IBOutlet weak var tblLang: UITableView!{
        didSet{
            tblLang.registerXIB(CellIdentifiers.LanguageCell.rawValue)
        }
    }
    
    @IBOutlet weak var heightTbl: NSLayoutConstraint!
    
    
    var dataSource:TableViewDataSource?
    var langArray = [LanguageData](){
        didSet{
            configureTbl()
        }
    }
    
    var shouldLoad = true
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
      }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
//        getData()
    }
    
    func getData(){
        UserEP.getLanguage.request(loader: shouldLoad, success: { (res) in
            guard let data = res as? LanguagesModel else { return }
            self.langArray = data.data ?? []
            self.shouldLoad = false
            self.configureTbl()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
    }
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    
    @IBAction func btnActionAdd(_ sender: Any) {
//        guard let vc = R.storyboard.details.addLanguageVC() else { return }
//        Router.shared.pushVC(vc: vc)
    }
    
    
    func configureTbl(){
           heightTbl.constant = CGFloat(50 * 2)
        dataSource = TableViewDataSource(items: langArray, tableView: tblLang, cellIdentifier: CellIdentifiers.LanguageCell.rawValue, configureCellBlock: { (cell, item, index) in
                     guard let mCell = cell as? LanguageCell else { return }
                     mCell.item = item
                 }, aRowSelectedListener: { (index, item) in
                   
                 }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
              tblLang.delegate = dataSource
              tblLang.dataSource = dataSource
              tblLang.reloadData()
       }
    
}
