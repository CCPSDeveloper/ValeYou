//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

extension UIApplication {
    
    class func topVC(controller: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
        if let navigationController = controller as? UINavigationController {
            return topVC(controller: navigationController.visibleViewController)
        }
        if let tabController = controller as? UITabBarController {
            if let selected = tabController.selectedViewController {
                return topVC(controller: selected)
            }
        }
        if let presented = controller?.presentedViewController {
            return topVC(controller: presented)
        }
        return controller
    }
    
  
    static var statusBarHeight: CGFloat {
          if #available(iOS 11.0, tvOS 11.0, *) {
              let value = self.shared.delegate?.window??.safeAreaInsets.top ?? 0
              return value == 0 ? 20.0 : value
          } else {
              return 20.0
          }
      }
    /// EZSE: Get the top most view controller from the base view controller; default param is UIWindow's rootViewController
    public class func topViewController(_ base: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
        if let nav = base as? UINavigationController {
            return topViewController(nav.visibleViewController)
        }
        if let tab = base as? UITabBarController {
            if let selected = tab.selectedViewController {
                return topViewController(selected)
            }
        }
        if let presented = base?.presentedViewController {
            return topViewController(presented)
        }
        return base
    }
    
//    class func isRTL() -> Bool {
//        return UIApplication.shared.userInterfaceLayoutDirection == .rightToLeft
//    }
    
    public class func makeLoginAsRoot(){
   
    }
}
