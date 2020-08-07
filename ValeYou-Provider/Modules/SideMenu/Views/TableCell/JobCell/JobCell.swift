//
//  JobCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class JobCell: UITableViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var imgJob: UIImageView!
    @IBOutlet weak var btnPlaceBid: UIButton!
    @IBOutlet weak var lblJobName: UILabel!
    @IBOutlet weak var lblTime: UILabel!
    @IBOutlet weak var lblAddress: UILabel!
    @IBOutlet weak var viewBack: UIView!
    
    
    var item:Any?
//    {
//        guard let image = item as? [String] else { return }
//        self.imgJob.setImageKF(image, placeholder: nil)
//    }
    
    //MARK: - Cell Initialization Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        
        Utility.dropShadow(mView: btnPlaceBid, radius: 2, color: .lightGray, size: CGSize(width: 0, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
