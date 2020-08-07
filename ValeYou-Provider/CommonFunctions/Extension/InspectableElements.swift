//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

extension UIButton {
    
    @IBInspectable
    open var exclusiveTouchEnabled : Bool {
        get {
            return self.isExclusiveTouch
        }
        set(value) {
            self.isExclusiveTouch = value
        }
    }
}

extension UIView {
    
    func applyGradient() {
        
        let gradient = CAGradientLayer()
        gradient.colors = [
            UIColor(red:0.2, green:0.84, blue:0.27, alpha:1).cgColor,
            UIColor(red:0.05, green:0.19, blue:0.15, alpha:1).cgColor
        ]   // your colors go here
        gradient.locations = [0, 1]
        gradient.startPoint = CGPoint(x: 0.01, y: 0)
        gradient.endPoint = CGPoint(x: 1, y: 1)
        gradient.cornerRadius = 4
        gradient.frame = self.bounds
        self.layer.insertSublayer(gradient, at: 0)
    }
    
    
    @IBInspectable
    open var shadowOffset: CGSize {
        get {
            return layer.shadowOffset
        }
        set(value) {
            layer.shadowOffset = value
        }
    }
    
    @IBInspectable
    open var shadowOpacity: Float {
        get {
            return layer.shadowOpacity
        }
        set(value) {
            layer.shadowOpacity = value
        }
    }
    
    @IBInspectable
    open var shadowRadius: CGFloat {
        get {
            return layer.shadowRadius
        }
        set(value) {
            self.layer.masksToBounds = false
            layer.shadowRadius = value
        }
    }
    
    @IBInspectable
    open var shadowPath: CGPath? {
        get {
            return layer.shadowPath
        }
        set(value) {
            layer.shadowPath = value
        }
    }
    
    @IBInspectable
    var shadowColor: UIColor {
        get {
            return  UIColor(cgColor: layer.borderColor ?? UIColor.clear.cgColor)
        }
        set {
            layer.shadowColor = newValue.cgColor
        }
    }
    
    @IBInspectable
    var borderWidth : CGFloat {
        set {
            layer.borderWidth = newValue
        }
        get {
            return layer.borderWidth
        }
    }
    
    @IBInspectable
    var cornerRadius: CGFloat  {
        get {
            return  layer.cornerRadius
        }
        set {
            self.clipsToBounds = true
            layer.cornerRadius = newValue
        }
    }
    
    @IBInspectable
    var borderColor: UIColor {
        get {
            return  UIColor(cgColor: layer.borderColor ?? UIColor.clear.cgColor)
        }
        set {
            layer.borderColor = newValue.cgColor
        }
    }
    
    @IBInspectable
    open var isCircularCorner : Bool {
        get {
            return true
        }
        set(value) {
            if value{
                let cornerRadius = min(frame.height/2, frame.width/2)
                layer.cornerRadius = cornerRadius
                layer.masksToBounds = true
            }
        }
    }
    
}

