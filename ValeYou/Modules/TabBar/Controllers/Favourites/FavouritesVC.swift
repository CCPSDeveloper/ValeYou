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
    var items = [GetFavouritesData](){
        didSet{
            configTable()
        }
    }
    //MARK: - Properties
    var tblDataSource:TableViewDataSource?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
       
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        self.getFavs()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configTable()
    }
    
    func configTable(){
        tblDataSource = TableViewDataSource(items: items, tableView: tblFav, cellIdentifier: CellIdentifiers.FavouriteCell.rawValue, configureCellBlock: { (cell, item, indexPath) in
            guard let mCell = cell as? FavouriteCell  , let item = item as? GetFavouritesData else { return }
             mCell.item = item
            mCell.accepted = {
                 UserEP.addToFavourite(provider_id: item.id, status:  2).request(loader: true, success: { (res) in
                    guard let res = res as? BasicResponse, let indexPath = indexPath else { return }
                    print(indexPath as Any)
 
//                     self.items.remove(at: indexPath.row)
//                                   // delete the table view row
//                    self.tblFav.deleteRows(at: [indexPath], with: .fade)
//                    self.configTable()
                 }) { (error) in
                    guard let error = error else { return }
                    Toast.shared.showAlert(type: .apiFailure, message: error)
                }

            }
        }, aRowSelectedListener: { (index, item) in
            
            guard let index = index as? IndexPath, let item = item as? GetFavouritesData else { return }
            let vc = R.storyboard.details.providerProfileVC()!
            vc.userId = item.providerID
            vc.isFav = item.status == 1 ? true : false
            Router.shared.pushVC(vc: vc)
 
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblFav.delegate = tblDataSource
        tblFav.dataSource = tblDataSource
        tblFav.reloadData()
    }

    func getFavs(){
        
        UserEP.getFavouriteList.request(loader: true, success: { (res) in
            guard let res = res as? GetFavourites else { return }
            self.items = res.data
             
        }) { (error) in
            guard let error = error else { return }
            Toast.shared.showAlert(type: .apiFailure, message: error)
        }
        
    }

}
