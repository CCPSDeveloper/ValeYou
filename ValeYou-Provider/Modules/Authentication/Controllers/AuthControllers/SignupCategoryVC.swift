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
    @IBOutlet weak var radioRemote: UIImageView!
    
    @IBOutlet weak var radioLocal: UIImageView!
    
    @IBOutlet weak var tblCat: UITableView!{
        didSet{
            tblCat.registerXIB(CellIdentifiers.CategoryCell.rawValue)
        }
    }
    @IBOutlet weak var heightView: NSLayoutConstraint!{
        didSet{
            heightView.constant = UIScreen.main.bounds.size.height - 230
        }
    }
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    var categories = [CategoriesData]()
    var selectedIndex = [Int]()
    var data:SignupData?
    var selectedData = ""
    var openedIndex = -2
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        getCategories()
    }
    
    func getCategories(){
        ProviderEP.getCategories.request(loader: true, success: {[weak self] (res) in
            guard let data = res as? Categories else { return }
            self?.categories = data.data ?? []
            self?.configureTable()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    
    //MARK: - IBAction Methods
    @IBAction func btnActionLocalJobs(_ sender: Any) {
        radioLocal.image = #imageLiteral(resourceName: "circle-home.png")
        radioRemote.image = #imageLiteral(resourceName: "circle")
    }
    
    
    
    @IBAction func btnActionRemote(_ sender: Any) {
        radioRemote.image = #imageLiteral(resourceName: "circle-home.png")
        radioLocal.image = #imageLiteral(resourceName: "circle")
    }
    
    
    
    func configureTable(){
        dataSource = TableViewDataSource(items: categories, tableView: tblCat, cellIdentifier: CellIdentifiers.CategoryCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? CategoryCell else { return }
            mCell.controller = self
           
            mCell.index = index?.row ?? -2
            if self.selectedIndex.count == 1{
                mCell.openIndex = self.selectedIndex[0]
            }else{
                mCell.openIndex = -1
            }
            mCell.category = item as? CategoriesData
            mCell.item = item
            mCell.didSelectSubCategory = {[weak self]
                item in
                    let priceView = SetPriceView(frame: /self?.view.bounds)
                    self?.view.addSubview(priceView)
                    priceView.didSelectPrice = { [weak self] price in
                    let category = self?.categories[/self?.selectedIndex.first]
                    let subCat = item
                        subCat.price = price
                    let arr = [subCat]
                        if category?.selectedSubCategory == nil{
                            category?.selectedSubCategory = arr
                        }else{
                            let arr = category?.selectedSubCategory?.filter({$0.id == item.id})
                            if arr?.count ?? 0 > 0 {
                                let mArray = category?.selectedSubCategory as NSArray?
                                let index = mArray?.index(of: subCat)
                                category?.selectedSubCategory?.remove(at: index ?? 0)
                                category?.selectedSubCategory?.append(subCat)
                            }else{
                                category?.selectedSubCategory?.append(subCat)
                            }
                        }
                    self?.categories[/self?.selectedIndex.first] = category!
                    self?.dataSource?.items = self?.categories
                    self?.tblCat.reloadData()
                }
            }
        }, aRowSelectedListener: { (index, item) in
            if self.selectedIndex.contains(index.row){
                self.selectedIndex.removeAll()
            }else{
                self.selectedIndex.removeAll()
                self.selectedIndex.append(index.row)
            }
            self.tblCat.reloadData()
            
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil, scrollToBottom: nil)
        tblCat.dataSource = dataSource
        tblCat.delegate = dataSource
        tblCat.reloadData()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionSignup(_ sender: Any) {
        var data = [[String:Any]]()
        for cat in self.categories{
            if cat.selectedSubCategory != nil{
                var mArray = [[String:Any]]()
                for sub in cat.selectedSubCategory ?? []{
                    let dict = ["id":/sub.id,"price":/sub.price] as [String : Any]
                    mArray.append(dict)
                }
                let dict = ["category_id":/cat.id,"subcategory":mArray] as [String : Any]
                data.append(dict)
            }
        }
        selectedData = data.toJson()
        self.data?.selectedData = selectedData
        print(selectedData)
       
        if selectedData == "[]"{
            Toast.shared.showAlert(type: .validationFailure, message: AlertMessage.CATEGORY_EMPTY)
        }else{
            guard let vc = R.storyboard.authentication.drivingLicenseVC() else { return }
                   vc.data = self.data
                   Router.shared.pushVC(vc: vc)
        
        }
        
    }
    
}
