//
//  FavouritesVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FavouritesVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var tblFav: UITableView!{
        didSet{
            tblFav.registerXIB(CellIdentifiers.FavouriteCell.rawValue)
        }
    }
    @IBOutlet weak var viewBack: UIView!

    //MARK: - Properties
    var tblDataSource:TableViewDataSource?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
       getData()
    }
    
    func getData(){
        ProviderEP.getFavouriteList.request(loader: true, success: { (res) in
            
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configTable()
    }
    
    func configTable(){
        tblDataSource = TableViewDataSource(items: [1,1,1,11,1,1], tableView: tblFav, cellIdentifier: CellIdentifiers.FavouriteCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? FavouriteCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblFav.delegate = tblDataSource
        tblFav.dataSource = tblDataSource
        tblFav.reloadData()
    }

   

}
