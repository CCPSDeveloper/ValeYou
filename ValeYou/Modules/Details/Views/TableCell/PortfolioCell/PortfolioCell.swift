//
//  PortfolioCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class PortfolioCell: UITableViewCell {
    
    @IBOutlet weak var imgPortfolio: UIImageView!
    
    @IBOutlet weak var lblDesc: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
    
    var item:Any?{
        didSet{
            guard let data = item as? PortfolioData else { return }
            imgPortfolio.setImageKF(APIConstant.mediaBasePath + /data.image, placeholder: UIImage(named:"placeholder"))
            lblTitle.text = /data.title
            lblDesc.text = /data.description
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
