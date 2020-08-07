//
//  CategoryCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CategoryCell: UITableViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var backView: UIView!
    @IBOutlet weak var lblCategory: UILabel!
    @IBOutlet weak var imgCategory: UIImageView!
    @IBOutlet weak var imgArrow: UIImageView!
    @IBOutlet weak var tblSubCategory: UITableView!{
        didSet{
            tblSubCategory.registerXIB(CellIdentifiers.SubCategoryCell.rawValue)
        }
    }
    @IBOutlet weak var tblHeight: NSLayoutConstraint!
    
    @IBOutlet weak var lblSelectedSubCategory: UILabel!
    
    //MARK: - Properties
    var dataSource:TableViewDataSource?
    
    var item:Any?{
        didSet{
            if let data = item as? CategoriesData {
                lblCategory.text = /data.name
                imgCategory.setImageKF(APIConstant.mediaBasePath + /data.image, placeholder:#imageLiteral(resourceName: "placeholder.png"))
                configureTable(items: data.subCategories ?? [])
                // if /data.selectedSubCategory != ""{
                //lblSelectedSubCategory.text = /data.selectedSubCategory + " from $" + /data.selectedSubCategoryPrice
                //   }
            }
        }
    }
    
    var controller:UIViewController?
    var openIndex = -1
    var index = -2
    var didSelectSubCategory:((_ item:SubCategories)->())?
    var category:CategoriesData?
    
    
    //MARK: - Cell Initialization Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: backView, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func configureTable(items:[SubCategories]){
        if openIndex == index{
            tblHeight.constant = CGFloat(items.count * 53)
        }else{
            tblHeight.constant = 0
        }
        
        dataSource = TableViewDataSource(items:items, tableView: tblSubCategory, cellIdentifier: CellIdentifiers.SubCategoryCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? SubCategoryCell else { return }
            mCell.item = item
            if index?.row == items.count - 1{
                mCell.lblLine.isHidden = true
            }else{
                mCell.lblLine.isHidden = false
            }
            if let data = item as? SubCategories{
                print(self.category?.selectedSubCategory?.count)
                let cat = self.category?.selectedSubCategory?.filter({$0.name == data.name})
                if cat?.count ?? 0 > 0 {
                    mCell.imgTick.image = #imageLiteral(resourceName: "where-tick-green.png")
                }else{
                    mCell.imgTick.image = #imageLiteral(resourceName: "circle")
                }
            }
            
        }, aRowSelectedListener: { (index, item) in
            //self.selectedIndex = index.row
            guard let data = item as? SubCategories else {return}
            self.didSelectSubCategory?(data)
            
            //
            //                    let priceView = SetPriceView(frame: /self?.view.bounds)
            //                    self?.view.addSubview(priceView)
            //                    priceView.didSelectPrice = { [weak self] price in
            //                        let category = self?.categories[/self?.selectedIndex]
            //                        category?.selectedSubCategory = name
            //                        category?.selectedSubCategoryPrice = price
            //                        category?.selectedSubId = id
            //                        self?.categories[/self?.selectedIndex] = category!
            //                        self?.dataSource?.items = self?.categories
            //                        self?.tblCat.reloadData()
            //                    }
            
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil, scrollToBottom: nil)
        tblSubCategory.dataSource = dataSource
        tblSubCategory.delegate = dataSource
        tblSubCategory.reloadData()
    }
}
