//
//  InboxCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 28/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class InboxCell: UITableViewCell {

    @IBOutlet weak var viewBack: UIView!
    var item:Any?{
        didSet{
            
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 2, color: .lightGray, size: CGSize(width: 0.5, height: 0.5))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
}
