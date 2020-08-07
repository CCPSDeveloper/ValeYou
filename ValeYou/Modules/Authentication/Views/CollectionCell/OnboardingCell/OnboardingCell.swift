//
//  OnboardingCell.swift
//  Pynkiwi
//
//  Created by Pankaj Sharma on 15/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class OnboardingCell: UICollectionViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var imgVw: UIImageView!
    @IBOutlet weak var imgWidth: NSLayoutConstraint!{
        didSet{
            imgWidth.constant = UIScreen.main.bounds.size.width - 50
        }
    }
    
    var item:Any?{
        didSet{
            guard let img = self.item as? UIImage else { return }
            imgVw.image = img
        }
        
    }
    

    
    //MARK: - Life Cycle Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        
    }


}
