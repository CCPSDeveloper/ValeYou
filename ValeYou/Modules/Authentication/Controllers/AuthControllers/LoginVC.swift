//
//  LoginVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 24/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import ACFloatingTextfield_Swift
import CoreLocation
import FBSDKLoginKit
import GoogleSignIn

class LoginVC: UIViewController, CLLocationManagerDelegate{
    
    //MARK: - IBOutlets
    @IBOutlet weak var tfEmail: ACFloatingTextfield!
    @IBOutlet weak var tfPassword: ACFloatingTextfield!
    var locationManager:CLLocationManager!
    var latitude:Double = 0.0
    var longitude:Double = 0.0
    let loginManager = LoginManager()
    
    
    //MARK: - Life Cycle Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //GOOGLE LOGIN
        GIDSignIn.sharedInstance()?.presentingViewController = self
        GIDSignIn.sharedInstance().delegate = self
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func validate()->String{
        if /tfEmail.text?.isEmpty || /tfPassword.text?.isEmpty{
            return AlertMessage.REQUIRED_EMPTY
        }else if !Utility.isValidEmail(testStr: /tfEmail.text){
            return AlertMessage.INVALID_EMAIL
        }
        return ""
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionLogin(_ sender: Any) {
        tfEmail.endEditing(true)
        tfPassword.endEditing(true)
        let valid = validate()
        if valid == ""{
            UserEP.login(email: /tfEmail.text, password: /tfPassword.text, deviceType: "2", deviceToken: "123456", latitude: "\(latitude)", longitude: "\(longitude)").request(loader: true, success: { (res) in
                guard let data = res as? Login else { return }
                UserPreference.shared.data = data.data
                UserPreference.shared.setLoggedIn(true)
                guard let vc = R.storyboard.tabBar.tabVC() else { return }
                Router.shared.pushVC(vc: vc)
                Router.shared.setDrawer()
            }) { (error) in
                Toast.shared.showAlert(type: .apiFailure, message: /error)
            }
        }
    }
    
    @IBAction func btnActionSignup(_ sender: Any) {
        guard let vc = R.storyboard.authentication.signupVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func btnActionForgot(_ sender: Any) {
        guard let vc = R.storyboard.authentication.forgotPassVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func googleLogin(_ sender: Any) {
        GIDSignIn.sharedInstance()?.signOut()
        GIDSignIn.sharedInstance()?.signIn()
    }
    
    @IBAction func facebookLogin(_ sender: Any) {
        loginManager.logOut()
        Profile.current = nil
        loginManager.logIn(permissions:[.publicProfile,.email], viewController: self, completion: loginManagerDidComplete(_:))
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else {return}
        latitude = location.coordinate.latitude
        longitude = location.coordinate.longitude
        locationManager.stopUpdatingLocation()
    }
    
}
