//
//  ProviderBidCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import Cosmos

class ProviderBidCell: UITableViewCell {

    //MARK: - IBOutlets
    @IBOutlet weak var viewChat: UIView!
    
    @IBOutlet weak var cosmosView: CosmosView!
    @IBOutlet weak var descLbl: UILabel!
    @IBOutlet weak var distanceLbl: UILabel!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var viewDecline: UIView!
    @IBOutlet weak var bidBtn: UIButton!
    @IBOutlet weak var ratingLbl: UILabel!

    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewAccept: UIView!
   
    var item:Any?{
        didSet{
            guard let item = item as? Bid else { return }
            self.nameLbl.text = item.providerFirstName
            self.descLbl.text = item.providerDescription
            self.cosmosView.rating = Double(item.avgrating)
            self.cosmosView.text = "\(item.avgrating)"
            //            self.ratingLbl.text = "\(item.avgrating)"
            var meteres = Measurement(value: Double(item.distance), unit: UnitLength.meters)
            self.distanceLbl.text = "\(meteres.convert(to: UnitLength.miles)) miles"
            self.bidBtn.setTitle("Placed Bid\n$\(item.price)", for: .normal)
            self.bidBtn.titleLabel?.textAlignment = .center
        }
    }
    
    var accepted:((Bool)->())?
    
    //MARK: - Cell Initialization methods
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewChat, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewAccept, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewDecline, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func btnActionAccept(_ sender: Any) {
        accepted?(true)
    }
    
    @IBAction func btnActionReject(_ sender: Any) {
        accepted?(false)
    }
}
