//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import Nuke
import SDWebImage
extension UIImageView {
    func setImageKF(_ imageOrURL: Any?,placeholder:UIImage?) {
    if let _image = imageOrURL as? UIImage {
      image = _image
    } else if let url = URL(string: /(imageOrURL as? String)) {
//      var request = ImageRequest(url: url)
//      request.memoryCacheOptions.isWriteAllowed = true
//      request.priority = .high
//
//      Nuke.loadImage(with: request, options: ImageLoadingOptions(transition: .fadeIn(duration: 0.33)), into: self)
        self.sd_setImage(with:url,placeholderImage: placeholder)
    }
  }
    
    
    func setImage(urlString: String){
        let url = URL(string:(APIConstant.mediaBasePath + urlString))
        print(url)
        self.sd_setImage(with: url , placeholderImage: #imageLiteral(resourceName: "placeholder"))
    }
}

extension UIView{
    // For insert layer in Foreground
    func addGradientLayerInForeground(frame: CGRect, colors:[UIColor]){
        let gradient = CAGradientLayer()
        gradient.frame = frame
        gradient.colors = colors.map{$0.cgColor}
        self.layer.addSublayer(gradient)
    }
    // For insert layer in background
    func addGradientLayerInBackground(frame: CGRect, colors:[UIColor]){
        let gradient = CAGradientLayer()
        gradient.frame = frame
        gradient.colors = colors.map{$0.cgColor}
        self.layer.insertSublayer(gradient, at: 0)
    }
}

