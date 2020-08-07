
/*
import Foundation
class Loader {
    
    static let shared = Loader()
    
    fileprivate let keyWindow = UIApplication.shared.keyWindow
    fileprivate lazy var loader: FeSpinnerTenDot = {
        return FeSpinnerTenDot(view: self.keyWindow, withBlur: false)
    }()
    
    func start() {
        //guard let root = ((UIApplication.shared.delegate as? AppDelegate)?.window?.rootViewController) else { return }
        keyWindow?.addSubview(loader)
        loader.show()
    }
    
    func stop() {
        loader.dismiss()
        loader.removeFromSuperview()
    }
    
}
*/


import Foundation
import UIKit
import Lottie

class Loader: NSObject {
    
    static let shared = Loader()
    
    let viewTemp = UIView(frame: UIScreen.main.bounds)
    var animatedView:AnimationView = AnimationView(name: "mexApp_Loader")
    
    
    
    func start() {
        
        guard let keyWindow = UIApplication.shared.keyWindow else { return }
        
        self.animatedView.frame = CGRect(x: 0, y: 0, width: 400, height: 400)
        self.animatedView.center = self.viewTemp.center
        self.animatedView.contentMode = .scaleAspectFill
        self.animatedView.loopMode = .loop
        self.viewTemp.addSubview(self.animatedView)
        self.animatedView.play()
        keyWindow.addSubview(self.viewTemp)
        keyWindow.bringSubviewToFront(self.viewTemp)
        self.viewTemp.backgroundColor = UIColor.black.withAlphaComponent(0.54)
        self.viewTemp.isHidden = false
        
    }
    
    func stop() {
        
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + DispatchTimeInterval.seconds(0) ) {
            self.animatedView.stop()
            self.viewTemp.isHidden = true
        }
        
    }
    
    
    
}


