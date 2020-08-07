//
//  SideCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SideCell: UITableViewCell {
    
    @IBOutlet weak var lblMenu: UILabel!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var imgIcon: UIImageView!
    
    var item:Any?{
        didSet{
            guard let data = item as? [String:Any] else { return }
            lblMenu.text = data["name"] as? String
            imgIcon.image = data["icon"] as? UIImage
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        
    }
    
}
