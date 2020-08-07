//
//  ProviderCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class ProviderCell: UICollectionViewCell {

    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var lblDesc: UILabel!
    @IBOutlet weak var lblDistance: UILabel!
    
    
    @IBOutlet weak var viewBack: UIView!
    
    var item:Any?{
        didSet{
            guard let data = item as? ProjectList else { return }
            lblTitle.text = /data.title
            lblDesc.text = /data.description
            lblDistance.text = "\(Double(round(/data.distance * 10)/10))" + " " + "milesAway".localize
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

}
