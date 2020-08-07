//
//  SetPriceView.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SelectPicturesView: UIView {

    //MARK: - IBOutlets
    @IBOutlet weak var backView: UIView!
    @IBOutlet var contentVie: UIView!
    @IBOutlet weak var cvPhotos: UICollectionView!
    @IBOutlet weak var viewCam: UIView!
    @IBOutlet weak var camBtn : UIButton!
    @IBOutlet weak var submitBtn : UIButton!

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
        Bundle.main.loadNibNamed("SelectPicturesView", owner: self, options: nil)
        self.contentVie.frame = self.bounds
        self.contentVie.autoresizingMask = [.flexibleWidth,.flexibleHeight]
        self.addSubview(contentVie)
        
        viewCam.addDashedBorder(width:
            150, height: 150, lineWidth: 2, lineDashPattern: [3,2], strokeColor: .lightGray, radius: 10, fillColor: .clear)
        
    }
    
    @IBAction func btnActionCam(_ sender: Any) {
        
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionClose(_ sender: Any) {
        self.removeFromSuperview()
    }
}
