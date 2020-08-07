//
//  AddPortfolio.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class AddPortfolio: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewTitle: UIView!
    @IBOutlet weak var viewDesc: UIView!
    @IBOutlet weak var viewCompletionDate: UIView!
    
    @IBOutlet weak var viewUpload: UIView!
    
    @IBOutlet weak var imgPort: UIImageView!
    @IBOutlet weak var tfDate: UITextField!
    
    @IBOutlet weak var tfTitle: UITextField!
    
    @IBOutlet weak var tfDesc: UITextField!
    
    @IBOutlet weak var imgHeight: NSLayoutConstraint!
    
    var selectedPicture:UIImage?
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
           Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewTitle, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewDesc, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewCompletionDate, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
           
        Utility.dropShadow(mView: viewUpload, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
           
        imgHeight.constant = 0
        tfDate.setInputViewDatePicker(mode: .date, target: self, selector: #selector(dateSelected))
       }

    
    
    @objc func dateSelected(){
          if let datePicker = self.tfDate.inputView as? UIDatePicker {
              let dateformatter = DateFormatter()
              dateformatter.dateFormat = "dd/MM/yyyy"
            tfDate.text = dateformatter.string(from: datePicker.date)
          }
          self.tfDate.resignFirstResponder()
      }
    
    
    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionSave(_ sender: Any) {
        let valid = validation()
        if valid == ""{
           UserEP.addPortfolio(title: /tfTitle.text, description: /tfDesc.text, image: selectedPicture ?? UIImage()).request(loader: true, success: { (res) in
                Router.shared.popFromInitialNav()
            }) { (error) in
                Toast.shared.showAlert(type: .apiFailure, message: /error)
            }
        }
    }
    
    @IBAction func btnActionCancel(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    
    @IBAction func btnActionUpload(_ sender: Any) {
        DispatchQueue.main.async {
                              AttachmentHandler.shared.showAttachmentActionSheet(vc: self)
                              AttachmentHandler.shared.imagePickedBlock = { [weak self] item in
                                  self?.selectedPicture = item
                                self?.imgPort.image = item
                                self?.imgHeight.constant = 130
                                 
                              }
                }
    }
    
    
    func validation()->String{
        if /tfTitle.text?.isEmpty || /tfDesc.text?.isEmpty || /tfDate.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }else if selectedPicture == nil{
            return AlertMessage.PICTURE_NOTSELECTED
        }
        return ""
    }
}
