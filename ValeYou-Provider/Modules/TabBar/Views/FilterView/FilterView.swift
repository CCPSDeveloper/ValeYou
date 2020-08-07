//
//  FilterView.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FilterView: UIView {
    
    //MARK: - IBOutlets

    
    
    //MARK: - View Initialization methods
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    func commonInit(){
        Bundle.main.loadNibNamed("FilterView", owner: self, options: nil)
        
    }

}
