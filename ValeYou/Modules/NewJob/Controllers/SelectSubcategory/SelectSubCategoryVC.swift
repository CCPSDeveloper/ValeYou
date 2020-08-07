//
//  SelectSubCategoryVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 02/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SelectSubCategoryVC: UIViewController {

    //MARK: - IBOutlets
    
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblSub: UITableView!{
        didSet{
            tblSub.registerXIB(CellIdentifiers.SubCategoryCell.rawValue)
        }
    }
    
    @IBOutlet weak var lblTitle: UILabel!
    
    //MARK: - Properties
    var screenTitle = ""
    var tvDataSource:TableViewDataSource?
     var catArray = [["name":"Haul Boxes","icon":#imageLiteral(resourceName: "Haul-icon-1")],["name":"Standard Pick-Up Load","icon":#imageLiteral(resourceName: "Standard-icon")],["name":"Standard Pick-Up Load + Trailer","icon":#imageLiteral(resourceName: "Stadnard-icomn-3")],["name":"Other","icon":#imageLiteral(resourceName: "other-icon")]]
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
        lblTitle.text = screenTitle
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configTable()
    }
    
    func configTable(){
               tvDataSource = TableViewDataSource(items: catArray, tableView: tblSub, cellIdentifier: CellIdentifiers.SubCategoryCell.rawValue, configureCellBlock: { (cell, item, index) in
                   guard let mCell = cell as? SubCategoryCell else { return }
                   mCell.item = item
               }, aRowSelectedListener: { (index, cell) in
                guard let vc = R.storyboard.newJob.addDescriptionVC() else { return }
                vc.screenTitle = (self.catArray[index.row]["name"] as? String)!
           Router.shared.pushVC(vc: vc)
               }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
            tblSub.delegate = tvDataSource
            tblSub.dataSource = tvDataSource
            tblSub.reloadData()
           }
    
    //MARK: - IBAction Methods

    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
}
