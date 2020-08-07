//
//  InterestPicker.swift
//  FollowMe
//
//  Created by Pankaj Sharma on 25/02/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class InterestPicker: UIView {
    
    //MARK: - IBOutlets
    @IBOutlet var contentView: UIView!
    @IBOutlet weak var backView: UIView!
    @IBOutlet weak var btnSubmit: UIButton!
    @IBOutlet weak var tblInterest: UITableView!{
        didSet{
            tblInterest.register(UINib(nibName: "CategoryCell", bundle: nil), forCellReuseIdentifier:  "CategoryCell"   )
                   
        }
    }
    
    //Properties
    
    var subCategories = [SubCategories]()
    var selectedSubCategory = ""
    
    var didSelectSubCat:((_ name:String,_ id:Int)->())?
    
    init(frame: CGRect,subCategories:[SubCategories]) {
        super.init(frame: frame)
        self.subCategories = subCategories
        commonInit()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }
    
    func commonInit(){
        Bundle.main.loadNibNamed("InterestPicker", owner: self, options: nil)
        self.contentView.frame = self.bounds
        self.contentView.autoresizingMask = [.flexibleWidth,.flexibleHeight]
        self.addSubview(self.contentView)
        tblInterest.delegate = self
        tblInterest.dataSource = self
        Utility.makeCornerRounds(mView: backView, radius: 10)
    
    }
    
    //MARK: - Button Action
    @IBAction func btnActionClose(_ sender: Any) {
        self.removeFromSuperview()
    }
    
    @IBAction func btnActionOk(_ sender: Any) {
//        if selectedInterests.count == 0{
//            self.delegate.didSelectInterests(intersts:"", error: "Please select atleast one category")
//        }else{
//            self.delegate.didSelectInterests(intersts: selectedInterests, error: nil)
//            self.removeFromSuperview()
//        }
    }
    
}

extension InterestPicker:UITableViewDelegate,UITableViewDataSource{
    
      func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
          return subCategories.count
      }
      
      func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
          let cell = tableView.dequeueReusableCell(withIdentifier: "CategoryCell") as! CategoryCell
        
        cell.lblCategory.text = /subCategories[indexPath.row].name
        cell.imgArrow.isHidden = true
        cell.imgCategory.setImageKF(APIConstant.mediaBasePath + /subCategories[indexPath.row].image, placeholder:#imageLiteral(resourceName: "place-bid-bg.png"))
          //cell.interestLabel.text = subCategories[indexPath.row]
          
        
          
          cell.selectionStyle = .none
          return cell
      }
      
      func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//          if selectedInterests.contains(interestArray[indexPath.row]){
//              let mArray = selectedInterests as NSArray
//              let index = mArray.index(of: interestArray[indexPath.row])
//              selectedInterests.remove(at: index)
//          }else{
//              selectedInterests.append(interestArray[indexPath.row])
//          }
        selectedSubCategory = /subCategories[indexPath.row].name
          tblInterest.reloadData()
        didSelectSubCat?(selectedSubCategory,/subCategories[indexPath.row].id)
        self.removeFromSuperview()
      }
      
      func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
      }
}
