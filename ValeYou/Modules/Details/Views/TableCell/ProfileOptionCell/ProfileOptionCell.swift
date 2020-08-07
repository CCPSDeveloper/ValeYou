//
//  ProfileOptionCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class ProfileOptionCell: UITableViewCell {
    
    //MARK: - IBOutlets
     @IBOutlet weak var lblOption: UILabel!
    
    //MARK: - Properties
    var item:Any?{
        didSet{
            guard let data = item as? String else {
                 return
            }
            lblOption.text = /data
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
