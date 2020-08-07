//
//  IdentityCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class IdentityCell: UITableViewCell {
    
    @IBOutlet weak var viewBack: UIView!
    
    @IBOutlet weak var imgIdentity: UIImageView!
    
    var item:Any?{
        didSet{
            
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        
    }
    
    @IBAction func btnActionEdit(_ sender: Any) {
        
    }
    
    @IBAction func btnActionDelete(_ sender: Any) {
        
    }
    
}
