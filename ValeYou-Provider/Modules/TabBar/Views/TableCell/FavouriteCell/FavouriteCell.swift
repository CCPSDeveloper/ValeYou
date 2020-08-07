//
//  FavouriteCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FavouriteCell: UITableViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var vieBack: UIView!
    
    
    //MARK: - Properties
    var item:Any?{
        didSet{
            
        }
    }

    //MARK: - Cell Initialization Methods
    override func awakeFromNib() {
        super.awakeFromNib()
         Utility.dropShadow(mView: vieBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
