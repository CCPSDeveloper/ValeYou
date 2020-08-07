//
//  JobMarkerView.swift
//  Shipster
//
//  Created by Techwin on 26/05/20.
//  Copyright © 2020 Techwin. All rights reserved.
//

import UIKit

class JobMarkerView: UIView {

    @IBOutlet var contentView: UIView!
    @IBOutlet weak var base: UIView!
    @IBOutlet weak var distance: UILabel!
    @IBOutlet weak var tailImage: UIImageView!
    
    @IBOutlet weak var baseImage: UIImageView!
    override init(frame: CGRect) {
          super.init(frame: frame)
          commonInit()
      }
      
      required init?(coder aDecoder: NSCoder) {
          super.init(coder: aDecoder)
          commonInit()
      }
      
      private func commonInit(){
          Bundle.main.loadNibNamed("JobMarkerView", owner: self, options: nil)
          addSubview(contentView)
        let circle = UIImage.circle(diameter: 40, color: UIColor.red)
        baseImage.image = circle
          contentView.frame = self.bounds
       // base.roundedCorners()

//          activityView.type = .circleStrokeSpin
//           activityView.color = APP_TINT_COLOR
      }

}
