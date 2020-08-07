//
//  ReviewCell.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit



class NotificationCell: UITableViewCell {

    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    
    @IBOutlet weak var name: UILabel!
    var notificationType : NotificationType?
    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var dateLbl: UILabel!
    @IBOutlet weak var desc: UILabel!
    
    var item:Any?{
        didSet{
            guard let item = item as? GetNotificationsData else { return }
            name.text = item.firstName + " " + item.lastName
            desc.text = item.message
            let date = Date(timeIntervalSince1970: Double(item.createdAt))//"\(item.createdAt)".fromTimeStampToDate()
            dateLbl.text = timeAgoSinceDate(date: date as NSDate, numericDates: true)
            
            img.set(item.providerImage)
            notificationType = NotificationType(rawValue: item.type)
            if let type = notificationType{
                if type == .bidOnJob{
                    
                }
            }
            
        }
    }
    
    
    //MARK: - Cell Initialization Methods
    override func awakeFromNib() {
        super.awakeFromNib()
        Utility.dropShadow(mView: viewBack, radius: 10, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
