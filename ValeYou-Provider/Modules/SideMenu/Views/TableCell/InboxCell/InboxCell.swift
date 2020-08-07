//
//  InboxCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 28/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class InboxCell: UITableViewCell {

    var item:Any?{
        didSet{
            
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
}
