//
//  FAQCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FAQCell: UITableViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var imgArrow: UIImageView!
    
    @IBOutlet weak var lblQuestion: UILabel!
    
    @IBOutlet weak var lblAnswer: UILabel!
    
    @IBOutlet weak var heightLbl: NSLayoutConstraint!
    
    @IBOutlet weak var viewBack: UIView!
    
    var item:Any?{
        didSet{
            
        }
    }
    
    
    //MARK: - Cell Initializatin Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
    }
    
    
}
