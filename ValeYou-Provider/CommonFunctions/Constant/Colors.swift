//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

enum Color: String {
    case colorLightPrimary = "colorLightPrimary"
    case placeholderGray = "colorPlaceholder"
    case colorPrimary = "colorPrimary"
    case colorText = "colorText"
    case bottomLineColor = "colorTextFieldBottomLine"
    
    
    var value: UIColor {
        return UIColor(named: self.rawValue) ?? UIColor()
    }
}

