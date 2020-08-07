//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

extension UIButton {
    func setButtonBorder(){
        self.backgroundColor = UIColor(named: "colorPrimary")
        self.setTitleColor(.white, for: .normal)
        self.borderWidth = 0
    }
    func removeButtonBorder(){
        self.backgroundColor = .white
        self.setTitleColor(UIColor(named: "colorText"), for: .normal)
        self.borderWidth = 1
        self.borderColor = UIColor(named: "colorPlaceholder") ?? .white
    }
}
