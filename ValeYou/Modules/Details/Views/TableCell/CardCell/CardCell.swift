//
//  CardCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CardCell: UITableViewCell {
    
    
    @IBOutlet weak var imgRadio: UIImageView!
    
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

        // Configure the view for the selected state
    }
    
}
