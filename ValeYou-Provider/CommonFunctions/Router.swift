//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import KYDrawerController
import UIKit


class Router{
    
    static let shared = Router()

    var initialNavigation:UINavigationController!
    
    func setInitialVC(){
        
        if UserPreference.shared.isLoggedIn() ?? false{
            setDrawer()
            return
        }
        guard let vc = R.storyboard.main.onboardingVC() else {return}
        initialNavigation = UINavigationController(rootViewController: vc)
        initialNavigation.isNavigationBarHidden = true
        let delegate = UIApplication.shared.delegate as! AppDelegate
        delegate.window?.rootViewController = initialNavigation
        delegate.window?.makeKeyAndVisible()
    }
    
    func pushVC(vc:UIViewController){
        initialNavigation.pushViewController(vc, animated: true)
    }
    
    func popFromInitialNav(){
        initialNavigation.popViewController(animated: true)
    }
    
     func setLoginAsRoot(){
            guard let vc = R.storyboard.authentication.loginVC() else {return}
            initialNavigation = UINavigationController(rootViewController: vc)
            initialNavigation.isNavigationBarHidden = true
            let delegate = UIApplication.shared.delegate as! AppDelegate
            delegate.window?.rootViewController = initialNavigation
            delegate.window?.makeKeyAndVisible()
        }
    
    var drawer:KYDrawerController?
    func setDrawer(){
        guard let vc = R.storyboard.tabBar.tabVC() else { return }
        initialNavigation = UINavigationController(rootViewController: vc)
        initialNavigation.isNavigationBarHidden = true
        let delegate = UIApplication.shared.delegate as! AppDelegate
        delegate.window?.rootViewController = initialNavigation
        delegate.window?.makeKeyAndVisible()

    }
    
    func changeMainVC(vc:UIViewController){
        initialNavigation = UINavigationController(rootViewController: vc)
        initialNavigation.isNavigationBarHidden = true
        drawer?.mainViewController = initialNavigation
    }
    
    func topController()->UIViewController?{
        return initialNavigation.topViewController
        
    }
    

    func share(url:URL,text:String){
        let firstActivityItem = text
        let secondActivityItem : NSURL = url as NSURL
        // If you want to put an image
       // let image : UIImage = UIImage(named: "image.jpg")!

        let activityViewController : UIActivityViewController = UIActivityViewController(
            activityItems: [firstActivityItem, secondActivityItem], applicationActivities: nil)

        // This lines is for the popover you need to show in iPad
        //activityViewController.popoverPresentationController?.sourceView = (sender as! UIButton)

        // This line remove the arrow of the popover to show in iPad
       // activityViewController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.allZeros
        //activityViewController.popoverPresentationController?.sourceRect = CGRect(x: 150, y: 150, width: 0, height: 0)

        // Anything you want to exclude
        activityViewController.excludedActivityTypes = [
            UIActivity.ActivityType.postToWeibo,
            UIActivity.ActivityType.print,
            UIActivity.ActivityType.assignToContact,
            UIActivity.ActivityType.saveToCameraRoll,
            UIActivity.ActivityType.addToReadingList,
            UIActivity.ActivityType.postToFlickr,
            UIActivity.ActivityType.postToVimeo,
            UIActivity.ActivityType.postToTencentWeibo
        ]

        topController()?.present(activityViewController, animated: true, completion: nil)
    }
}
