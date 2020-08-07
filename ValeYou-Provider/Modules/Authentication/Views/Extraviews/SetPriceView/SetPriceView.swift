//
//  SetPriceView.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SetPriceView: UIView {

    //MARK: - IBOutlets
    @IBOutlet weak var backView: UIView!
    @IBOutlet var contentVie: UIView!
    @IBOutlet weak var viewField: UIView!
    @IBOutlet weak var tfPrice: UITextField!
    
    var didSelectPrice:((_ price:String)->())?
    
    //MARK: - View Initialization
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }
    
    func commonInit(){
        Bundle.main.loadNibNamed("SetPriceView", owner: self, options: nil)
        self.contentVie.frame = self.bounds
        self.contentVie.autoresizingMask = [.flexibleWidth,.flexibleHeight]
        self.addSubview(contentVie)
        
        Utility.dropShadow(mView: viewField, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionClose(_ sender: Any) {
        self.removeFromSuperview()
    }
    
    @IBAction func btnActionOk(_ sender: Any) {
        if /tfPrice.text?.isEmpty{
            Toast.shared.showAlert(type: .validationFailure, message: "emptyPrice".localize)
            return
        }
        didSelectPrice?(/tfPrice.text)
        self.removeFromSuperview()
    }
    
}
