//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import AVFoundation
import Photos
import PhotosUI

//MARK:- EXTENSIONS
extension String: OptionalType {}
extension Int: OptionalType {}
extension Double: OptionalType {}
extension Bool: OptionalType {}
extension Float: OptionalType {}
extension CGFloat: OptionalType {}
extension CGRect: OptionalType {}
extension UIImage: OptionalType {}
extension IndexPath: OptionalType {}

prefix operator /

//unwrapping values
prefix func /<T: OptionalType>(value: T?) -> T {
    guard let validValue = value else { return T() }
    return validValue
}

typealias OptionalDictionary = [String : Any]?

protocol OptionalType { init() }


protocol MyTextFieldDelegate {
    func textFieldDidDelete(textField:MyTextField)
}

class MyTextField: UITextField {
    
    var myDelegate: MyTextFieldDelegate?
    
    override func deleteBackward() {
        super.deleteBackward()
        myDelegate?.textFieldDidDelete(textField: self)
    }
    
}
