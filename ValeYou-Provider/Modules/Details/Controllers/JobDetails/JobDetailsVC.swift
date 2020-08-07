//
//  JobDetailsVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

enum JobStatusType : Int{
    case complete = 4
    case ongoing = 3
    case cancelled = 2
    case upcoming = 1
    case all = 0
}
class JobDetailsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var descLbl: UILabel!
    
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    @IBOutlet weak var jobStatusLbl: UILabel!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewTop: UIView!
    @IBOutlet weak var cvPhotos: UICollectionView!{
        didSet{
            cvPhotos.registerXIB(CellIdentifiers.JobPhotoCell.rawValue)
        }
    }
    
    @IBOutlet weak var tfBid: UITextField!
    @IBOutlet weak var viewBid: UIView!
    
    var jobId:Int?
    var items : JobDetailsData?
    var dataSource:CollectionDataSource?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        configureCollection()
        getJobDetails()
    }
    
    func getJobDetails(){
        guard let id = jobId else {return}
              ProviderEP.getJobDetails(postId: "\(id)").request(loader: true, success: { (res) in
                  guard let res = res as? JobDetails else { return }
                  self.items = res.data
                  self.titleLbl.text = /self.items?.title
                  self.addressLbl.text = /self.items?.location
                  self.descLbl.text = /self.items?.dataDescription
                
                self.dateLbl.text = /self.items?.date.fromTimeStampToDate()
                let attributes = self.getJobTypeName(with: JobStatusType(rawValue: /self.items?.status)!)

                  self.jobStatusLbl.text = attributes.title
                  self.jobStatusLbl.textColor = attributes.color
                  self.configureCollection()
              }) { (error) in
                  Toast.shared.showAlert(type: .apiFailure, message: /error)
              }
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
         Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
         Utility.dropShadow(mView: viewBid, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    func configureCollection(){
        
        dataSource = CollectionDataSource(_items: self.items?.orderImages?.map({$0.images}) , _identifier: CellIdentifiers.JobPhotoCell.rawValue, _collectionView: cvPhotos, _size: CGSize(width: cvPhotos.frame.size.width / 2.6, height: cvPhotos.frame.size.height - 20), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil)
        dataSource?.configureCell = { (cell,item,index) in
            
            guard let mCell = cell as? JobPhotoCell else { return }
            mCell.item = item
        }
        
        cvPhotos.delegate = dataSource
        cvPhotos.dataSource = dataSource
        
    }
    
    typealias JobTypeAttributes = (title: String,color: UIColor)

    public func getJobTypeName(with type : JobStatusType) -> JobTypeAttributes{
        switch type{
        case .all:
            return ("All",.purple)
        case .cancelled:
            return ("Cancelled",.red)
        case .complete:
            return ("Completed",.green)
        case .upcoming:
            return ("Upcoming",#colorLiteral(red: 0, green: 0.6558215022, blue: 0.9967903495, alpha: 1))
        case .ongoing:
            return ("Ongoing",.orange)
        }
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionSubmit(_ sender: Any) {
        
        if tfBid.text!.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: "Please enter bid amount.")
        }
        else if jobId == nil{
            Toast.shared.showAlert(type: .validationFailure, message: "Invalid job id.")
        }
        else{
            ProviderEP.placeBid(postId: "\(jobId!)", price: tfBid.text!).request(loader: true, success: { (res) in
                guard let res = res as? PlaceBid else { return }
                for vc in self.navigationController!.viewControllers{
                    if vc is TabVC{
                        self.navigationController?.popToViewController(vc, animated: true)
                    }
                }
                // self.getJobDetails()
            }) { (error) in
                guard let error = error else { return }
                Toast.shared.showAlert(type: .apiFailure , message: error)
            }
        }
        
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
}


