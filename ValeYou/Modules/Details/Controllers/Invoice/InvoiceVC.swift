//
//  NewJobVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class InvoiceVC: UIViewController {
    
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewTop: UIView!
    
    @IBOutlet weak var viewBottom: UIView!
    @IBOutlet weak var jobTitleLbl: UILabel!
    //        @IBOutlet weak var shortDescriptionLbl: UILabel!
    @IBOutlet weak var descLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    
    @IBOutlet weak var priceLbl: UILabel!
    @IBOutlet weak var serviceLbl: UILabel!
    @IBOutlet weak var tipLbl: UILabel!
    @IBOutlet weak var subTotalLbl: UILabel!
    
    
    let kServiceCharges = 7.80
    let kTipAmount = 0.00
    
    var jobDetails : JobDetailsData?
    var bid : Bid?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewBottom, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        
        if let job = jobDetails{
            self.addressLbl.text = job.location
            self.jobTitleLbl.text = job.title
            //                       self.shortDescriptionLbl.text = job.dataDescription
            self.descLbl.text = job.dataDescription
            let dateSt = Double(job.date)!
            let date = Date(timeIntervalSince1970: dateSt)
            self.dateLbl.text = date.presentableFormat()
            
        }
        
        if let bid = bid{
             self.priceLbl.text = "$\(bid.price)"
            self.serviceLbl.text = "$\(kServiceCharges)"
            self.tipLbl.text = "$\(kTipAmount)"
            self.subTotalLbl.text = "$\(Double(bid.price) + kServiceCharges + kTipAmount)"
        }
        
    }
    
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionPay(_ sender: Any) {
        guard let vc = R.storyboard.details.paymentMethodsVC() else { return  }
        vc.jobDetails = jobDetails
        Router.shared.pushVC(vc: vc)
        
    }
    
}
