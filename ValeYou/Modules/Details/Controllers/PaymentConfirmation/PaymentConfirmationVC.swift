//
//  PaymentConfirmationVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class PaymentConfirmationVC: UIViewController {

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
    
    
//    let kServiceCharges = 7.80
//               let kTipAmount = 20.00
    
    var jobDetails : JobDetailsData?
    var bid : Bid?
    
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
    }
      
      //MARK: - IBAction Methods
    @IBAction func btnActionPay(_ sender: Any) {
       for vc in Router.shared.initialNavigation.viewControllers {
                     if vc is TabVC{
                         Router.shared.initialNavigation.popToViewController(vc, animated: true)
                     }
                 }
    }
}
