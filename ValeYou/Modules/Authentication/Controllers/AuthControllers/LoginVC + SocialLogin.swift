//
//  LoginVC + SocialLogin.swift
//  ValeYou
//
//  Created by Techwin on 04/08/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import FBSDKLoginKit
import GoogleSignIn


public enum SignupType : Int{
    case facebook = 1
    case google = 2
//    case twitter = 3
    case regular = 0
}

extension LoginVC{
    
    func sendGraphRequest(){
        
        //        loginManager.logIn(permissions: ["email"], from: self) { (result, error) in
        //            if error == nil{
        let params = ["fields" : "id, name, first_name, last_name, picture.type(large), email "]
        let graphRequest = GraphRequest.init(graphPath: "/me", parameters: params)
        let connection = GraphRequestConnection()
        connection.add(graphRequest) { (Connection, result, error) in
            if error == nil{
                let info = result as! [String : AnyObject]
                print(info["name"] as! String)
                let imageUrl = ((info["picture"] as! NSDictionary).value(forKey: "data") as! NSDictionary).value(forKey: "url") as! String
                //                        let name = info["name"] as! String
                let parameters = [
                    "name":info["name"] as! String ,
                    "socialId":info["id"] as! String,
                    "email": info["email"] as? String ?? "" ,
                    "image":imageUrl,
                    "socialAccountType": 1
                    ] as NSDictionary
                
               let fullName = info["name"] as! String
                let socialId = info["id"] as! String
                let email = info["email"] as! String
 
                print("parameters : \(parameters)")
                print("FBSDKAccessToken :\(String(describing: AccessToken.current))")
                
                self.checkIfSocial(id: socialId, type: .facebook, fullname: fullName , email:  email , phone: "", date_of_birth: "", bio: "", profileImage:  imageUrl)
                //                Router.shared.setDrawer()
                //                        guard let socialID = parameters["social_id"] as?  String else { return }
                //                self.signUpType = .facebook
                //                self.isSocialIdExists(prms: parameters as! [String : Any])
            }else{
//                self.alert(error!.localizedDescription)
                Toast.shared.showAlert(type: .apiFailure, message: error!.localizedDescription)

            }
        }
        connection.start()
        //            }else{
        //                self.alert("Login Error")
        //            }
        //        }
    }
    
    func loginManagerDidComplete(_ result: LoginResult) {
        switch result {
        case .cancelled:
            // self.alert("Login Cancelled", "User cancelled login.")
            print("Login Cancelled")
            break
        case .failed(let error):
//            self.alert("Login Fail", "Login failed with error \(error)")
            Toast.shared.showAlert(type: .apiFailure, message: "Login failed with error \(error)")

        //            break
        case .success(let grantedPermissions, _, _):
            self.sendGraphRequest()
            //            break
        }
    }
    
}

//GOOGLE

extension LoginVC: GIDSignInDelegate{  //MARK:- SIGN IN SDKs DELEGATES
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!,
              withError error: Error!) {
        if let error = error {
            if (error as NSError).code == GIDSignInErrorCode.hasNoAuthInKeychain.rawValue {
                print("The user has not signed in before or they have since signed out.")
            } else {
                print("\(error.localizedDescription)")
            }
            return
        }
        // Perform any operations on signed in user here.
        let userId = user.userID                  // For client-side use only!
        let idToken = user.authentication.idToken // Safe to send to the server
        let fullName = user.profile.name
        let givenName = user.profile.givenName
        let familyName = user.profile.familyName
        let email = user.profile.email
        print("GOOGLE SIGN IN DATA: \n \(String(describing: userId)) , \n \(String(describing: idToken)) , \n\(String(describing: fullName)) , \n\(String(describing: givenName)) , \n\(familyName ?? "no family name fetched"),  \n\(email ?? "No Email Found")")
        
        var parameters : [String : Any] = ["name":fullName ?? "" ,
                                           //                                           "social_id":idToken ?? "",
            "email":email ??  "",
            "socialId" : userId!,
            "socialAccountType":2
        ]
        
        if user.profile.hasImage{
            let pic = user.profile.imageURL(withDimension: 200)
            print("picture: \(pic!)")
            parameters["image" ] = pic!
        }
        //      self.signUpType = .google
        //      self.isSocialIdExists(prms: parameters )
        self.checkIfSocial(id: userId!, type: .google, fullname: fullName , email:  email ?? "" , phone: "", date_of_birth: "", bio: "", profileImage:  user.profile.hasImage ? (user.profile.imageURL(withDimension: 200)?.absoluteString ?? "") : "")
    }
    
    func sign(_ signIn: GIDSignIn!, didDisconnectWith user: GIDGoogleUser!,
              withError error: Error!) {
        // Perform any operations when the user disconnects from app here.
        // ...
    }
}

extension LoginVC{
    
    func checkIfSocial(id:String, type: SignupType, fullname: String? , email:  String?, phone:  String?, date_of_birth:  String?, bio:  String?, profileImage:  String?){
        UserEP.socialLogin(socialId: id, socialType:"\(type.rawValue)", deviceType: "2", deviceToken: "123")
            .request(loader: true, success: { (res) in
                guard let res = res as? Login,
                let isSignupCompleted = res.data?.status
                    else { return }
                            if isSignupCompleted == 1{
                                UserPreference.shared.data = res.data
                                UserPreference.shared.setLoggedIn(true)
                                Router.shared.setDrawer()
//                                guard let vc = R.storyboard.tabBar.tabBarVC() else { return }
//                                Router.shared.pushVC(vc: vc)
                            }else{
                                let vc = R.storyboard.authentication.signupVC()
                                vc?.signUpData.socialType = "\(type.rawValue)"
                                vc?.signUpData = SignupData(firstName: fullname, lastName: "", email: email, isNGO: "No", password: "", countryCode: "", phoneNum: phone, address: "", lat: "", long: "", age: "", securityCode: "", paypalId: "", description: "", selectedData: "", socialId: id, socialType: "\(type.rawValue)")
                                    //SignUpPayload(fullname: fullname, email: email, phone: phone, date_of_birth: date_of_birth, bio: bio, profileImage: profileImage,socialId: id)
                                self.navigationController?.pushViewController(vc!, animated: true)
                            }
            }) { (error) in
                if let error = error{
                    Toast.shared.showAlert(type: .apiFailure, message: error)
                }
        }
    }
 }
    
 
