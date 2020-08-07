//
//  JobDetailsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
enum JobDetailsVCCallers{
    case newJob
    case jobList
    
}
class JobDetailsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var jobTitleLbl: UILabel!
    @IBOutlet weak var shortDescriptionLbl: UILabel!
    @IBOutlet weak var descLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewTop: UIView!
    @IBOutlet weak var cvPhotos: UICollectionView!{
        didSet{
            cvPhotos.registerXIB(CellIdentifiers.JobPhotoCell.rawValue)
        }
    }
    
    @IBOutlet weak var cvPhotosHeight: NSLayoutConstraint!
    var job : UserJob?
    var caller : JobDetailsVCCallers = .jobList
    var jobId : Int?
    @IBOutlet weak var tfBid: UITextField!
    @IBOutlet weak var viewBid: UIView!
    
    @IBOutlet weak var tblProviders: UITableView!{
        didSet{
            tblProviders.registerXIB(CellIdentifiers.ProviderBidCell.rawValue)
        }
    }
    @IBOutlet weak var tblHeight: NSLayoutConstraint!{
        didSet{
            tblHeight.constant = 145 * 2
        }
    }
    
    var dataSource:CollectionDataSource?
    var tblDataSource:TableViewDataSource?
    
    //MARK: - Life Cycle Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        configureCollection()
        configureTable()
     }
    var items : JobDetailsData?
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        if let jobId = job?.id {
            getJobDetails(id: jobId)
        } else {
            if let id = jobId{
                getJobDetails(id: id)
            }
        }
    }
//    var jobDetail : JobDetailsData?
    func getJobDetails(id: Int){
        UserEP.getJobDetails(postId: "\(id)").request(loader: true, success: { (res) in
            guard let res = res as? JobDetails else { return }
            
            self.items = res.data
            
            //            self.titleLbl.text = /self.items?.title
            self.addressLbl.text = /self.items?.location
            self.descLbl.text = /self.items?.dataDescription
            self.dateLbl.text = /self.items?.date.fromTimeStampToDate()
            let attributes = getJobTypeName(with: UserJobType(rawValue: /self.items?.status)!)
            //             self.jobStatusLbl.text = attributes.title
            //            self.jobStatusLbl.textColor = attributes.color
            if /self.items?.orderImages.count > 0{
                self.cvPhotosHeight.constant = 120
            }else{
                self.cvPhotosHeight.constant = 0
            }
            self.configureCollection()
            self.configureTable()
            
        }) { (error) in
            if let error = error{
                Toast.shared.showAlert(type: .apiFailure, message: error)
            }
        }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        if let job = job{
            self.addressLbl.text = job.location
            self.jobTitleLbl.text = job.title
            self.shortDescriptionLbl.text = job.datumDescription
            self.descLbl.text = job.datumDescription
            let dateSt = Double(job.date)!
            let date = Date(timeIntervalSince1970: dateSt)
            self.dateLbl.text = date.presentableFormat()
            //            self./.text = job.location
        }
    }
    
    func configureCollection(){
        dataSource = CollectionDataSource(_items: job?.orderImages.map({$0.images}), _identifier: CellIdentifiers.JobPhotoCell.rawValue, _collectionView: cvPhotos, _size: CGSize(width: 100, height: 100), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil)
        dataSource?.configureCell = { (cell,item,index) in
            
            guard let mCell = cell as? JobPhotoCell else { return }
            mCell.item = item
        }
        
        cvPhotos.delegate = dataSource
        cvPhotos.dataSource = dataSource
    }
    
    func configureTable(){
        tblDataSource = TableViewDataSource(items: self.items?.bids, tableView: tblProviders, cellIdentifier: CellIdentifiers.ProviderBidCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? ProviderBidCell else { return }
            mCell.item = item
//            mCell.accepted = {
////                guard let vc = R.storyboard.details.invoiceVC() else { return }
////                vc.jobDetails = self.items
////                vc.bid = item as? Bid
////                Router.shared.pushVC(vc: vc)
//
//            }
            mCell.accepted = { accepted in
                
                guard let bidItem = item as? Bid
                    ,let items = self.items
                    else { return }
                 let jobCategory = JobCategory(rawValue: "\(items.jobType)")
                if jobCategory! == .local{
                    UserEP.respondBid(type: accepted ? "1" :"2", post_id: "\(bidItem.id)" , provider_id: "\(bidItem.providerID)").request(loader: true, success: { (res) in
                        guard let res = res as? RespondBid else { return }
                        if res.success == 1{
                            if accepted{
                            for vc in Router.shared.initialNavigation.viewControllers {
                                if vc is TabVC{
                                    Router.shared.initialNavigation.popToViewController(vc, animated: true)
                                }
                            }
                            }else{
                                self.getJobDetails(id: items.id)
                            }
                        }
                        
                    }) { (error) in
                        if let error = error{
                            Toast.shared.showAlert(type: .apiFailure, message: error)
                        }
                    }
                }else{
                    //remote job
                    Toast.shared.showAlert(type: .apiFailure, message: "Remote job payment flow is in development.")
                    
                }
            }
        }, aRowSelectedListener: { (index, item) in
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblProviders.delegate = tblDataSource
        tblProviders.dataSource = tblDataSource
        tblProviders.reloadData()
    }
    
//    func responseToBid(id: Int, accept: Bool, completion: @escaping ((Bool) -> Void)){
//    }
    
    
    //MARK: - IBAction Methods
    @IBAction func btnActionSubmit(_ sender: Any) {
        
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
}
