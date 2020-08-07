//
//  SelectCategoryVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 02/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SelectCategoryVC: UIViewController {
    
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
    
    var postData = PostData()
    var providerId: Int?{
        didSet{
            guard let id = providerId else {
                return
            }
            postData.providerId = "\(id)"
        }
    }
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    var categories = [CategoriesData](){
        didSet{
            selectedType = .remote
        }
    }
    var selectedIndex = [Int]()
    //    var data: PostData?
    var selectedData = ""
    var openedIndex = -2
    
    var localJobCategories = [CategoriesData](){
        didSet{
            selectedType = .local
        }
    }
    
    var selectedType : JobCategory = .remote{
        didSet{
            self.configureTable()
        }
    }
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        radioRemote.image = #imageLiteral(resourceName: "circle-home.png")
        radioLocal.image = #imageLiteral(resourceName: "circle")
        getCategories(type: .remote)
        print("provider id is set : \(providerId as Any)")
    }

    
    func getCategories(type: JobCategory){
        UserEP.getCategories(type: type.rawValue).request(loader: true, success: {[weak self] (res) in
            guard let data = res as? Categories else { return }
            if type == .remote{
                self?.categories = data.data ?? []

            }else{
                self?.localJobCategories = data.data ?? []
             }
            
            self?.configureTable()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }

    
    //MARK: - IBAction Methods
    @IBAction func btnActionLocalJobs(_ sender: Any){
        radioLocal.image = #imageLiteral(resourceName: "circle-home.png")
        radioRemote.image = #imageLiteral(resourceName: "circle")
        selectedType = .local
        if localJobCategories.count == 0{
            self.getCategories(type: selectedType)
        }
    }
    
    @IBAction func btnActionRemote(_ sender: Any){
        radioRemote.image = #imageLiteral(resourceName: "circle-home.png")
        radioLocal.image = #imageLiteral(resourceName: "circle")
        selectedType = .remote
        if categories.count == 0{
            self.getCategories(type: selectedType)
        }
    }
    
    func configureTable(){
        dataSource = TableViewDataSource(items: selectedType == .local ? localJobCategories : categories, tableView: tblCat, cellIdentifier: CellIdentifiers.CategoryCell.rawValue, configureCellBlock: { (cell, item, index) in
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
                //                let priceView = SetPriceView(frame: /self?.view.bounds)
                //                self?.view.addSubview(priceView)
                //                priceView.didSelectPrice = { [weak self] price in
                var category : CategoriesData?
                if self?.selectedType == .remote{
                 category = self?.categories[/self?.selectedIndex.first]
                }else{
                    category = self?.localJobCategories[/self?.selectedIndex.first]
                }
                let subCat = item
                subCat.price = ""
                subCat.name = item.name
                print("subCat.name :\(  subCat.name)")
                let arr = [subCat]
                if category?.selectedSubCategory == nil{
                    category?.selectedSubCategory = arr
                }else{
                    let arr = category?.selectedSubCategory?.filter({$0.id == item.id})
                    if arr?.count ?? 0 > 0 {
                        let mArray = category?.selectedSubCategory as NSArray?
                        let index = mArray?.index(of: subCat)
                        //                        category?.selectedSubCategory?.remove(at: index ?? 0)
                        category?.selectedSubCategory?.removeAll(where: { $0.id == item.id })
                        //                        category?.selectedSubCategory?.append(subCat)
                    }else{
                        category?.selectedSubCategory?.append(subCat)
                    }
                }
                 if self?.selectedType == .remote{
                self?.categories[/self?.selectedIndex.first] = category!
                 }else{
                    self?.localJobCategories[/self?.selectedIndex.first] = category!
                 }
                self?.dataSource?.items = self?.categories
                self?.tblCat.reloadData()
                //                print("categories:",self?.categories.f as Any)
                //                }
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
    
    @IBAction func btnActionNext(_ sender: Any) {
        var data = [[String:Any]]()
        var subCatStrings = [String]()
        
        for cat in ( selectedType == .remote ? self.categories : self.localJobCategories){
            if cat.selectedSubCategory != nil{
                var mArray = [[String:Any]]()
                for sub in cat.selectedSubCategory ?? []{
                    let dict = ["id":/sub.id,"price":/sub.price] as [String : Any]
                    mArray.append(dict)
                    print("sub.name: \(sub.name)")
                    print("sub.id: \(sub.id)")
                    print("sub.categoryId: \(sub.categoryId)")
                    
                    subCatStrings.append(sub.name ?? "12")
                    print("sub cats: ",self.postData.selectedSubCategories)
                }
                
                let dict = ["category_id":/cat.id,"subcategory":mArray] as [String : Any]
                data.append(dict)
            }
        }
        selectedData = data.toJson()
        self.postData.selectedData = selectedData
        self.postData.selectedSubCategories = subCatStrings
        //        let subCats = self.categories.map({$0.selectedSubCategory})
        //        self.postData.selectedSubCategories?.append(subCats.first.map({$0.name}))
        print(postData as Any)
        print(selectedData)
        if selectedData == "[]"{
            Toast.shared.showAlert(type: .validationFailure, message: AlertMessage.CATEGORY_EMPTY)
        }else{
            guard let vc = R.storyboard.newJob.addInfoTypeVC() else { return }
            vc.postData = self.postData
            Router.shared.pushVC(vc: vc)
        }
    }
    
}

struct PostData {
    
    var city:String?
    //    var country: String?
    var state:String?
    var zipCode:String?
    var phoneNum:String?
    var address:String?
    var apartmentNo : String?
    var lat:Double?
    var long:Double?
    var title: String?
    var description:String?
    var estimationPrice: String?
    var startPrice: String?
    var endPrice: String?
    var startTime: String?
    var endTime: String?
    var endDate: String?
    var startDate:String?
    var estimationTime: String?
    var selectedData:String?
    var type: String? // now / today/ futre
    var time: String?
    var date: String?
    var images: [UIImage]?
    var selectedSubCategories: [String]?
    var providerId : String?
}
