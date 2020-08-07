//
//  ProviderCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import Cosmos
class ProviderCell: UICollectionViewCell {

    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var img: UIImageView!

    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var distanceLbl: UILabel!
    @IBOutlet weak var cosmosView: CosmosView!
    @IBOutlet weak var reviewsLbl: UILabel!
    
    var item:Any?{
        didSet{
            guard let item = item as? GetProviderListData else { return }
            nameLbl.text = item.firstName + " " + item.lastName
            var meteres = Measurement(value: Double(item.distance), unit: UnitLength.meters)
            self.distanceLbl.text = "\(meteres.convert(to: UnitLength.miles)) miles away"
            self.reviewsLbl.text = " "
            self.cosmosView.rating = item.avgRating
            self.cosmosView.text = "\(item.avgRating)"
            self.img.set(item.image)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

}
