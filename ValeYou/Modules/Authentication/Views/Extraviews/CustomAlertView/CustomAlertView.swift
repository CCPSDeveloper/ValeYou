//
//  SetPriceView.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CustomAlertView: UIView {

    //MARK: - IBOutlets
    @IBOutlet weak var backView: UIView!
    @IBOutlet var contentVie: UIView!
    @IBOutlet weak var lblMessage: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var viewCancel: MyGradient!
    @IBOutlet weak var viewOk: MyGradient!
    
    var isForSingleButton = false
    var title = ""
    var message = ""
    var okAction:(()->())?
    
    //MARK: - View Initialization
    init(frame: CGRect,title:String? = "information".localize,message:String,singleButton:Bool) {
        super.init(frame: frame)
        self.title = title ?? "information".localize
        self.message = message
        self.isForSingleButton = singleButton
        commonInit()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }
    
    func commonInit(){
        Bundle.main.loadNibNamed("CustomAlertView", owner: self, options: nil)
        self.contentVie.frame = self.bounds
        self.contentVie.autoresizingMask = [.flexibleWidth,.flexibleHeight]
        self.addSubview(contentVie)
        lblTitle.text = title
        lblMessage.text = message
        if isForSingleButton{
            viewCancel.isHidden = true
        }
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionClose(_ sender: Any) {
        self.removeFromSuperview()
    }
    
    @IBAction func btnActionCance(_ sender: Any) {
        self.removeFromSuperview()
    }
    @IBAction func btnActionOk(_ sender: Any) {
        okAction?()
        self.removeFromSuperview()
    }
    
}
