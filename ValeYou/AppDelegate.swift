//
//  AppDelegate.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 23/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import CoreData
import IQKeyboardManagerSwift
import GoogleMaps
import FBSDKCoreKit
import GoogleSignIn

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        //FB
               ApplicationDelegate.shared.application(
                          application,
                          didFinishLaunchingWithOptions: launchOptions
                      )
               // Initialize sign-in
        GIDSignIn.sharedInstance().clientID = SDKs.google.clientId
//                GIDSignIn.sharedInstance().delegate = self
               
        
        GMSServices.provideAPIKey("AIzaSyAf5Us7xQgnm0hggbsj86lrwzKSCykgErE")
        IQKeyboardManager.shared.enable = true
        Router.shared.setInitialVC()
 
        return true
    }

   @available(iOS 9.0, *)
   func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
       let facebookDidHandle = ApplicationDelegate.shared.application(
           app,
           open: url,
           sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
           annotation: options[UIApplication.OpenURLOptionsKey.annotation]
       )
       let googleHandle = GIDSignIn.sharedInstance().handle(url)
       
 
       return facebookDidHandle || googleHandle //|| twitterHandle
   }
   
   // <= iOS 8
   func application(_ application: UIApplication,
                    open url: URL, sourceApplication: String?, annotation: Any) -> Bool {
       let facebookDidHandle = ApplicationDelegate.shared.application(
           application,
           open: url,
           sourceApplication:  sourceApplication,
           annotation: annotation
       )
            let googleHandle = GIDSignIn.sharedInstance().handle(url)
       return facebookDidHandle || googleHandle
   }

    
    //MARK: - Notification Delegate Method
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        let token = deviceToken.map { String(format: "%02.2hhx", $0) }.joined()
        print(token)
        print(deviceToken.description)
        if let uuid = UIDevice.current.identifierForVendor?.uuidString {
            print(uuid)
        }
//        UserDefaults.standard.set(token, forKey: UserDefaultKeys.deviceToken)
        UserDefaults.standard.synchronize()
    }
    
    
    // MARK: - Core Data stack

    lazy var persistentContainer: NSPersistentContainer = {
        /*
         The persistent container for the application. This implementation
         creates and returns a container, having loaded the store for the
         application to it. This property is optional since there are legitimate
         error conditions that could cause the creation of the store to fail.
        */
        let container = NSPersistentContainer(name: "ValeYou_Provider")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                 
                /*
                 Typical reasons for an error here include:
                 * The parent directory does not exist, cannot be created, or disallows writing.
                 * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                 * The device is out of space.
                 * The store could not be migrated to the current model version.
                 Check the error message to determine what the actual problem was.
                 */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }

}

//extension AppDelegate: GIDSignInDelegate{
//    
//    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
//        //
//    }
//    
//}
