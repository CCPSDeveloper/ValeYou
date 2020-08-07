//
//  SelectCategoryCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 02/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class SelectCategoryCell: UICollectionViewCell {
    
    //MARK: - IBOutlets
    @IBOutlet weak var backView: UIView!
    @IBOutlet weak var vHeight: NSLayoutConstraint!{
        didSet{
            vHeight.constant = UIScreen.main.bounds.size.width / 2.8
        }
    }
    
    @IBOutlet weak var vWidth: NSLayoutConstraint!{
        didSet{
            vWidth.constant = UIScreen.main.bounds.size.width / 2.8
        }
    }
    
    @IBOutlet weak var imgCat: UIImageView!
    @IBOutlet weak var lblCat: UILabel!
    
    
    //MARK: - Properties
    var item:Any?{
        didSet{
            guard let data = item as? [String:Any] else { return }
            imgCat.image = data["icon"] as? UIImage
            lblCat.text = data["name"] as? String
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: backView, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

}
