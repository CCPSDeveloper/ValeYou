//
//  FavouriteCell.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class FavouriteCell: UITableViewCell {
    
    @IBOutlet weak var img: UIImageView!
    //MARK: - IBOutlets
    @IBOutlet weak var vieBack: UIView!
    @IBOutlet weak var favbtn: UIButton!
    
    @IBOutlet weak var roleLbl: UILabel!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var descLbl: UILabel!
    var accepted:(()->())?

    //MARK: - Properties
    var item:Any?{
        didSet{
            guard let item = item as? GetFavouritesData else { return }
            img.set(item.providerImage)
            nameLbl.text = item.providerfirstName + " " + item.providerlastName
            roleLbl.text = "\(item.avgRating)"
            descLbl.text = item.datumDescription
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
    
    @IBAction func favBtn(_ sender: Any) {
        accepted?()
    }
}
