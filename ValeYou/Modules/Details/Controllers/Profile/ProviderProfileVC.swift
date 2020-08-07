//
//  ProviderProfileVC.swift
//  ValeYou
//
//  Created by Techwin on 31/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class ProviderProfileVC: UIViewController {

    //MARK: - IBOutlets
    
    @IBOutlet weak var lblVerified: UILabel!
    @IBOutlet weak var lblAddress: UILabel!
    
    @IBOutlet weak var favBtn: UIButton!
    
    @IBOutlet weak var lblDesc: UILabel!
    @IBOutlet weak var lblCategory: UILabel!
    @IBOutlet weak var lblAge: UILabel!
    @IBOutlet weak var lblPhone: UILabel!
    @IBOutlet weak var lbEmail: UILabel!
    @IBOutlet weak var lblName: UILabel!
    @IBOutlet weak var providerActionStack: UIStackView!
 
    var isFav = false{
        didSet{
            DispatchQueue.main.async{
                self.favBtn.setImage(self.isFav == true ? #imageLiteral(resourceName: "favorites-icon") : #imageLiteral(resourceName: "favorite-footer"), for: .normal)
            }
        }
    }
    var userId : Int?
    @IBOutlet weak var imgVwProfile: UIImageView!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tblOptions: UITableView!{
        didSet{
            tblOptions.registerXIB(CellIdentifiers.ProfileOptionCell.rawValue)
        }
    }
    @IBOutlet weak var heightTbl: NSLayoutConstraint!
    var providerData : GetProviderListData?
    var profileData : GetProviderDetailsData?
    var optionArray = ["Languages".localize,
                       "Portfolio".localize,
                       "myService".localize,
                       "businessHour".localize,
                       "IdentityVerification".localize,
                       "certificate&Award".localize,
                       "reviews".localize,
                       "changePassword".localize,
                       "deactivateAccount".localize,
                       "deleteAccount".localize]
    struct ProfileItems {
        var item: String
        var vc: String
    }
    
    var items = [ProfileItems]()
    
    var dataSource:TableViewDataSource?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
         setupView()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        setData()
    }
    

    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
    }
    
    func setData(){
        
        if let providerData = self.providerData{
            optionArray = ["Languages".localize,
                           "Portfolio".localize,
                           //             "myService".localize,
                "businessHour".localize,
                //             "IdentityVerification".localize,
                "certificate&Award".localize,
                //             "reviews".localize,
                //             "changePassword".localize,
                //             "deactivateAccount".localize,
                //             "deleteAccount".localize
            ]
            
            items = [
                ProfileItems(item: "Languages".localize, vc: R.storyboard.details.languageVC.identifier),
                ProfileItems(item: "Portfolio".localize, vc: R.storyboard.details.portfolioVC.identifier),
//                ProfileItems(item: "businessHour".localize, vc: R.storyboard.details.businessHourVC()),
                ProfileItems(item: "certificate&Award".localize, vc: R.storyboard.details.certificatesVC.identifier)
             ]
            self.configureTbl()
            lblName.text = providerData.firstName + " " + /providerData.lastName
            lbEmail.text = "fetching"///providerData.email
            lblPhone.text = "fetching"///providerData.countryCode + /providerData.phone
            lblAge.text = "fetching"//"\(/providerData.age)"
            lblAddress.text = providerData.address
            isFav = providerData.fav == 0 ? true : false
            imgVwProfile.set(providerData.image)
            //                  var categories = ""
            //                  for cat in providerData.subCategories ?? []{
            //                      categories += /cat.categoryName
            //                      if /cat.categoryName != /data?.subCategories?.last?.categoryName{
            //                          categories += ","
            //                      }
            //                  }
            lblCategory.text = "fetching"//categories
            //        lblDesc.text = /data?.description
            imgVwProfile.setImageKF(/providerData.image, placeholder: #imageLiteral(resourceName: "user.png"))
            self.lblDesc.text = providerData.datumDescription
            
            self.getProfile(id: providerData.id)
            
        }
        else if let userId = userId{
            items = [
                ProfileItems(item: "Languages".localize, vc: R.storyboard.details.languageVC.identifier),
                ProfileItems(item: "Portfolio".localize, vc: R.storyboard.details.portfolioVC.identifier),
                //                ProfileItems(item: "businessHour".localize, vc: R.storyboard.details.businessHourVC()),
                ProfileItems(item: "certificate&Award".localize, vc: R.storyboard.details.certificatesVC.identifier)
            ]
            self.configureTbl()
            
            self.getProfile(id: userId)
        }
        else{
            let data = UserPreference.shared.data
            lblName.text = /data?.firstName + " " + /data?.lastName
            lbEmail.text = /data?.email
            lblPhone.text = /data?.countryCode + /data?.phone
            lblAge.text = "\(/data?.age)"
            lblAddress.text = /data?.address
            imgVwProfile.set(/data?.image)

            var categories = ""
            for cat in data?.subCategories ?? []{
                categories += /cat.categoryName
                if /cat.categoryName != /data?.subCategories?.last?.categoryName{
                    categories += ","
                }
            }
            lblCategory.text = categories
            //        lblDesc.text = /data?.description
            imgVwProfile.setImageKF(/data?.image, placeholder: #imageLiteral(resourceName: "user.png"))
        }
        
        
    }
    
    func getProfile(id : Int){
                 UserEP.userGetProviderDetail(providerId: "\(id)").request(loader: true, success: { (res) in
                        guard let res = res as? GetProviderDetails else { return }
                         
                        let data = res.data
                    self.profileData = data
                        self.lblName.text = data.firstName + " " + data.lastName
                        self.lbEmail.text = data.email///providerData.email
                        self.lblPhone.text = data.phone///providerData.countryCode + /providerData.phone
                        self.lblAge.text = "N/A"//"\(/providerData.age)"
                        self.lblAddress.text = data.address
                        self.lblDesc.text = data.dataDescription
                        self.imgVwProfile.setImageKF(data.image, placeholder: #imageLiteral(resourceName: "user.png"))

        //                isFav = data.fav == 1 ? true : false
        //                var categories = ""
        //                for cat in data.subCategories ?? []{
        //                    categories += /cat.categoryName
        //                    if /cat.categoryName != /data?.subCategories?.last?.categoryName{
        //                        categories += ","
        //                    }
        //                }
         //                let subCats = data.providerCategories.map({ $0.subCategories})
        //                let cats = subCats?.map({$0.subCategory.name})
        //                self.lblCategory.text = cats?.joined(separator: ",") //.map({$0.map({$0.name})})

                        
                    }) { (error) in
                        guard let error = error else { return }
                        Toast.shared.showAlert(type: .apiFailure, message: error)
                    }
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionEdit(_ sender: Any) { //FAVOURITE BTN
        if let providerData = providerData{
             UserEP.addToFavourite(provider_id: providerData.id, status: isFav == false ? 1 : 2).request(loader: true, success: { (res) in
                guard let res = res as? BasicResponse else { return }
                if res.success == 1{
                    self.isFav = !self.isFav
                }else{
                    
                }
            }) { (error) in
                guard let error = error else { return }
                Toast.shared.showAlert(type: .apiFailure, message: error)
            }
        }else{
            
        guard let vc = R.storyboard.details.editProfileVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    }
     
    func configureTbl(){
        heightTbl.constant = CGFloat(50 * optionArray.count)
        dataSource = TableViewDataSource(items: optionArray, tableView: tblOptions, cellIdentifier: CellIdentifiers.ProfileOptionCell.rawValue, configureCellBlock: { (cell, item, index) in
                  guard let mCell = cell as? ProfileOptionCell else { return }
                  mCell.item = item
              }, aRowSelectedListener: { (index, item) in
                self.handleSelection(index:index.row)
              }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
           tblOptions.delegate = dataSource
           tblOptions.dataSource = dataSource
           tblOptions.reloadData()
    }
    
    @IBAction func messsageBTn(_ sender: Any) {
        
    }
    
    @IBAction func hireBtn(_ sender: Any) {
        guard let id = self.providerData?.id else { return }
        guard let vc = R.storyboard.newJob.selectCategoryVC() else { return }
        vc.providerId = id
        Router.shared.pushVC(vc: vc)
    }
    
    func handleSelection(index:Int){
        
        let item = items[index]
        let stb = R.storyboard.details
        switch item.vc{
//        case R.storyboard.details.portfolioVC():
////            vc.l
//               Router.shared.pushVC(vc: vc)
//            break
            
        case stb.languageVC.identifier:
            
            guard let vc = R.storyboard.details.languageVC(),
            let data = profileData else { return }
            vc.langArray = data.providerLanguage
             Router.shared.pushVC(vc: vc)
            break
            
            //        case 0:
//            Router.shared.pushVC(vc: vc)
            //        case 1:
            //            guard let vc = R.storyboard.details.portfolioVC() else { return }
            //            Router.shared.pushVC(vc: vc)
            //        case 2:
            //            guard let vc = R.storyboard.authentication.signupCategoryVC() else { return }
            //            Router.shared.pushVC(vc: vc)
            //        case 3:
            //            guard let vc = R.storyboard.details.businessHourVC() else { return }
            //            Router.shared.pushVC(vc: vc)
            //        case 4:
            //            guard let vc = R.storyboard.details.identitiesVC() else { return }
            //            Router.shared.pushVC(vc: vc)
            //        case 5:
            //            guard let vc = R.storyboard.details.certificatesVC() else{ return }
            //            Router.shared.pushVC(vc: vc)
            //        case 6:
            //            guard let vc = R.storyboard.sideMenu.reviewsVC() else { return}
            //            Router.shared.pushVC(vc: vc)
            //        case 7:
            //            guard let vc = R.storyboard.authentication.changePassVC() else { return }
            //            Router.shared.pushVC(vc: vc)
            //        case 8:
            //            break
            //        case 9:
        //            break
        default:
            let vc = R.storyboard.details().instantiateViewController(identifier: item.vc)
           Router.shared.pushVC(vc: vc)
        }
    }
}
