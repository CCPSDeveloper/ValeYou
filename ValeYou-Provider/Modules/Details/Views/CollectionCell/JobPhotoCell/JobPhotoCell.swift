//
//  JobPhotoCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class JobPhotoCell: UICollectionViewCell {
    @IBOutlet weak var img : UIImageView!
    var item:Any?
    {
        didSet{
            guard let img = item as? String else { return }
            self.img.setImage(urlString: img)
        }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        
    }

}
