//
//  SubCategoryCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 22/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SubCategoryCell: UITableViewCell {

    //MARK: - IBOutlet Methods
    
    @IBOutlet weak var imgTick: UIImageView!
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var lblLine: UILabel!
    
    @IBOutlet weak var iconImg: UIImageView!
    //MARK: - Properties
    var item:Any?{
        didSet{
            guard let data = item as? SubCategories else { return }
            lblTitle.text = /data.name
            
        }
    }
    
    //MARK: - Cell Initialization Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
