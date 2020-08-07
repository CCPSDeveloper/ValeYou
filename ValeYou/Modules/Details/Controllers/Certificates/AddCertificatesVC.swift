//
//  AddCertificatesVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class AddCertificatesVC: UIViewController {

    //MARK: - IBOutlets
    
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewUpload: UIView!
    @IBOutlet weak var viewDesc: UIView!
    @IBOutlet weak var viewTitle: UIView!
    
    @IBOutlet weak var tfTitle: UITextField!
    
    @IBOutlet weak var tfDesc: UITextField!
    
    @IBOutlet weak var imgCert: UIImageView!
    
    
    @IBOutlet weak var imgHeight: NSLayoutConstraint!
    
    var selectedPicture:UIImage?
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewUpload, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewDesc, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewUpload, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewTitle, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        imgHeight.constant = 0
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionSave(_ sender: Any) {
        let valid = validation()
           if valid == ""{
//            UserEP.addCertificate(title: /tfTitle.text, description: /tfDesc.text, image: selectedPicture ?? UIImage()).request(loader: true, success: { (res) in
//                   Router.shared.popFromInitialNav()
//               }) { (error) in
//                   Toast.shared.showAlert(type: .apiFailure, message: /error)
//               }
           }
    }
    
    
    
    
    @IBAction func btnActionCancel(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionUploadPhoto(_ sender: Any) {
        DispatchQueue.main.async {
                      AttachmentHandler.shared.showAttachmentActionSheet(vc: self)
                      AttachmentHandler.shared.imagePickedBlock = { [weak self] item in
                          self?.selectedPicture = item
                        self?.imgCert.image = item
                        self?.imgHeight.constant = 130
                         
                      }
        }
    }
    
    func validation()->String{
        if /tfTitle.text?.isEmpty || /tfDesc.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }else if selectedPicture == nil{
            return AlertMessage.PICTURE_NOTSELECTED
        }
        return ""
    }
    
}
