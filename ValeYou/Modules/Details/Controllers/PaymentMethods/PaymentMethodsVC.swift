//
//  PaymentMethodsVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class PaymentMethodsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var jobTitleLbl: UILabel!
    //        @IBOutlet weak var shortDescriptionLbl: UILabel!
    @IBOutlet weak var descLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    
    @IBOutlet weak var priceLbl: UILabel!
    @IBOutlet weak var serviceLbl: UILabel!
    @IBOutlet weak var tipLbl: UILabel!
    @IBOutlet weak var subTotalLbl: UILabel!
    
//    
//    let kServiceCharges = 7.80
//               let kTipAmount = 20.00
    
    var jobDetails : JobDetailsData?
    var bid : Bid?
    
    @IBOutlet weak var viewTop: UIView!
    @IBOutlet weak var tblCard: UITableView!{
        didSet{
            tblCard.registerXIB(CellIdentifiers.CardCell.rawValue)
        }
    }
    
    @IBOutlet weak var tblMethod: UITableView!{
        didSet{
            tblMethod.registerXIB(CellIdentifiers.PaymentMethodCell.rawValue)
        }
    }
    
    var dsCard:TableViewDataSource?
    var dsMethod:TableViewDataSource?
    var selectedItem = -1
    
    //MARK: - Life Cycle Method
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        configureTbl()
        configureMethodTbl()
        
//        if let job = jobDetails{
//            self.addressLbl.text = job.location
//            self.jobTitleLbl.text = job.title
//            //                       self.shortDescriptionLbl.text = job.dataDescription
//            self.descLbl.text = job.dataDescription
//            let dateSt = Double(job.date)!
//            let date = Date(timeIntervalSince1970: dateSt)
//            self.dateLbl.text = date.presentableFormat()
//            
//        }
    }
    
    func configureTbl(){
        dsCard = TableViewDataSource(items: [1,2], tableView: tblCard, cellIdentifier: CellIdentifiers.CardCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? CardCell else { return }
            mCell.item = item
            if index?.row == self.selectedItem{
                mCell.imgRadio.image = #imageLiteral(resourceName: "radio.png")
            }
        }, aRowSelectedListener: { (index, item) in
            self.selectedItem = index.row
            self.tblCard.reloadData()
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblCard.delegate = dsCard
        tblCard.dataSource = dsCard
        tblCard.reloadData()
    }
    
    func configureMethodTbl(){
        dsMethod = TableViewDataSource(items: [1,2,3], tableView: tblMethod, cellIdentifier: CellIdentifiers.PaymentMethodCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? PaymentMethodCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblMethod.delegate = dsMethod
        tblMethod.dataSource = dsMethod
        tblMethod.reloadData()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionPay(_ sender: Any) {
        guard let vc = R.storyboard.details.paymentConfirmationVC() else { return }
        vc.jobDetails = jobDetails
        Router.shared.pushVC(vc: vc)
    }
    
}
