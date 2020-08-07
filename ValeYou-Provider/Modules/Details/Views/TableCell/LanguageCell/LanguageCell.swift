//
//  LanguageCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class LanguageCell: UITableViewCell {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var lblLang: UILabel!
    
    @IBOutlet weak var btnEdit: UIButton!
    @IBOutlet weak var btnDelete: UIButton!
    
    var item:Any?{
        didSet{
            guard let data = item as? LanguageData else { return }
            lblLang.text = /data.name + " (\(/data.type))"
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
    
    @IBAction func btnActionDelete(_ sender: Any) {
    }
    
    @IBAction func btnActionEdit(_ sender: Any) {
    }
    
    
}
