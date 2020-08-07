//
//  AddDescriptionVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 02/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

public enum AddNewJobType: Int{
    case remote = 0
    case local = 1
}

class AddDescriptionVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewBottom: UIView!
    @IBOutlet weak var viewTop: UIView!
    @IBOutlet weak var viewEstimatedTime: UIView!
    @IBOutlet weak var estimatedTimeTf: UITextField!
    @IBOutlet weak var shortDescTf: UITextField!
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var descTf: UIPlaceholderTextView!
    var postData = PostData()
    //MARK: - Properties
    var screenTitle = ""
    
    
    //MARK: - Life Cycle methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        descTf.placeholderColor = .placeholderText
        descTf.font = R.font.montserratBold(size: 17)
        descTf.placeholder = "Enter Description here..."
        descTf.placeholderFont = R.font.montserratBold(size: 17)
        descTf.textColor = .black
        
        lblTitle.text = screenTitle
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewBottom, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewTop, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView:  viewEstimatedTime, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionNext(_ sender: Any) {
        
        if descTf.text!.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: "Please provide some job description.")
        }else if estimatedTimeTf.text!.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: "Please enter estimated time.")
        }
        else if shortDescTf.text!.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: "Please enter Job Title.")
        }
        else{
            //api
            UserEP.userAddPost(title:shortDescTf.text!, description: descTf.text!, time: (postData.startTime!), date: (postData.startDate!), estimationTime: "", estimationPrice: "", selected_data: (postData.selectedData!), city: (postData.city)!, state: (postData.state)!, zipCode: (postData.zipCode)!, location: (postData.address)!, latitude: "\((postData.lat)!)", longitude: "\((postData.long)!)", type: (postData.type!),startPrice:postData.startPrice!, endPrice:postData.endPrice!, startTime: postData.startTime!, endTime:postData.endTime!, apartmentNo: (postData.apartmentNo!), jobType: 1, providerId: self.postData.providerId , image: postData.images).request(loader: true, success: { (data) in
                //
                print(data as Any)
                
                let view = CustomAlertView(frame: self.view.bounds, message:  self.postData.providerId == nil ? "A booking request has been sent to all available doers.".localize : "A booking request has been sent to this partiucular doer.".localize, singleButton: true)
                view.lblTitle.text = "Booking request"
                self.view.addSubview(view)
                view.okAction = {
                    //                    let vc = R.storyboard.details.jobDetailsVC()!
                    //                    //                    vc.job =
                    //                    Router.shared.pushVC(vc: vc)
                    for vc in Router.shared.initialNavigation.viewControllers {
                        if vc is TabVC{
                            Router.shared.initialNavigation.popToViewController(vc, animated: true)
                        }
                    }
                }
                
            }) { (error) in
                if let error = error{
                    Toast.shared.showAlert(type: .apiFailure, message: error)
                }
            }
        }
         //        guard let vc = R.storyboard.newJob.addInfoTypeVC() else { return }
        //        vc.screenTitle = self.screenTitle
        //        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
}
