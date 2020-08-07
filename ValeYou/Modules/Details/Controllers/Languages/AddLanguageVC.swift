//
//  AddLanguageVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import DropDown

class AddLanguageVC: UIViewController {

    //MARK: - IBOutlets
    @IBOutlet weak var viewLang: UIView!
    @IBOutlet weak var viewProficiency: UIView!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var tfLanguage: UITextField!
    
    @IBOutlet weak var tfProficiency: UITextField!
    

    var langArray = ["English","Spanish","Arabic","Portuguese","Russian","Japanese","Mandarin Chinese"]
    
    var typeArray = ["Basic","Conversational","Fluent"]
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
       Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewLang, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        Utility.dropShadow(mView: viewProficiency, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    

    //MARK: - IBAction Methods
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionLang(_ sender: Any) {
        let dropDown = DropDown()
        dropDown.dataSource = langArray
        dropDown.anchorView = sender as? UIButton
        dropDown.show()
        dropDown.selectionAction = { [unowned self] (index:Int,item:String) in
            self.tfLanguage.text = item
            dropDown.hide()
            
        }
    }
    
    @IBAction func btnActionFluency(_ sender: Any) {
          let dropDown = DropDown()
                   dropDown.dataSource = typeArray
                   dropDown.anchorView = sender as? UIButton
                   dropDown.show()
                   dropDown.selectionAction = { [unowned self] (index:Int,item:String) in
                       self.tfProficiency.text = item
                       dropDown.hide()
                       
                   }
                   
               
    }
    
    
    @IBAction func btnActionSave(_ sender: Any) {
        let valid = validation()
        if valid == ""{
           UserEP.addLanguage(name: /tfLanguage.text, type: /tfProficiency.text).request(loader: true, success: { (res) in
            
                Router.shared.popFromInitialNav()
            }) { (error) in
                Toast.shared.showAlert(type: .apiFailure, message: /error)
            }
        }
    }
    
    @IBAction func btnActionCancel(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    func validation()->String{
        if /tfLanguage.text?.isEmpty || /tfProficiency.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }
        return ""
    }
}
