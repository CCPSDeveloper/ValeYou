//
//  UserEP.swift
//  MEX
//
//  Created by Pankaj Sharma on 01/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import Moya

enum UserEP{
    case signup(firstName:String,lastName:String,email:String,password:String,phone:String,country_code:String,address:String,isNGO:String,latitude:String,longitude:String,age:String,socialSecurityNo:String,selectedData:String,deviceType:String,deviceToken:String,socialId:String?,driverLicense:UIImage?,image:UIImage?,paypalId:String,description:String,  socialType: String)
    
    case login(email:String,password:String,deviceType:String,deviceToken:String,latitude:String,longitude:String)
    
    case logout
    
    case changePassword(oldPassword:String,newPassword:String)
    
    case forgotPassword(email:String)
    
    case socialLogin(socialId:String,socialType:String,deviceType:String,deviceToken:String)
    
    case userAddPost(title:String,description:String,time:String,date:String,estimationTime:String,estimationPrice:String, selected_data:String,city:String,state:String,zipCode:String,location:String,latitude:String,longitude:String,type:String, startPrice: String, endPrice: String,startTime: String, endTime: String,apartmentNo: String,jobType: Int, providerId : String?, image:[UIImage]?)
    
    case userJobList(page:Int, limit: Int, type: Int)
    
    case getMapList(page:String,limit:String, latitude: String, longitude: String, search: String)
    
    case userGetProviderDetail(providerId: String)
    
    case getHomeListing(page:String,limit:String)
    
    case respondBid(type: String, post_id: String, provider_id: String)
    
    case changeAvailibility(type:String)
    
    case getJobDetails(postId:String)
    
    case getProfile(providerId:String)
    
    case editProfile(firstName:String,lastName:String,email:String,phone:String,countryCode:String,description:String,image:UIImage?,businessLicense:UIImage?,insurance:UIImage?,resume:UIImage?,driverLicense:UIImage?)
    
    case allContent(type:String)
    
    case placeBid(postId:String,price:String)
    case updateBid(postId:String,price:String)
    
    case myReviews
    
    case rateUser(userId:String,rating:String,description:String)
    case getCategories(type:String)
    case jobHistory(page:String,limit:String)
    case getNotifications
    case startJob(jobId:String,status:String)
    
    case getFavouriteList
    case addToFavourite(provider_id:Int,status:Int)
    case myJobs(page:String,limit:String,status:String)
    
    case addPortfolio(title:String,description:String,image:UIImage)
    case updatePortfolio(providerId:String,title:String,description:String,image:UIImage)
    case deletePortfolio(providerId:String)
    case getPortfolio
    
    case addLanguage(name:String,type:String)
    case updateLanguage(langId:String,name:String,type:String)
    case deleteLanguage(langId:String)
    case getLanguage
    
    case addBusinessHour(data:String,type:String)
    case getBusinessHour
    
    case addCertificate(title:String,description:String,image:String)
    case updateCertificate(certificateId:String,title:String,description:String,image:String)
    case deleteCertificate(certificateId:String)
    case getCertificates
    
    case readNotification(type:String,notificationId:String)
    
    
    case filterSearch(categoryId:String,distance:String,state:String)
}

extension UserEP:TargetType{
    var baseURL: URL {
        switch self{
        default: return URL(string: APIConstant.basePath)!
        }
        
    }
    
    var path: String {
        switch self {
        case .signup: return APIConstant.signup
        case .login: return APIConstant.login
        case .logout: return APIConstant.logout
        case .changePassword : return APIConstant.changePassword
        case .forgotPassword: return APIConstant.forgotPassword
        case .socialLogin:return APIConstant.socialLogin
        case .getMapList:return APIConstant.providerMapListing
        case .getHomeListing:return APIConstant.getHomeListing
        case .changeAvailibility: return APIConstant.provider_availability
        case .getJobDetails: return APIConstant.jobDetails
        case .getProfile: return APIConstant.getProfile
        case .editProfile: return APIConstant.editProfile
        case .userAddPost: return APIConstant.userAddPost
        case .userJobList: return APIConstant.userJobList
        case .allContent: return APIConstant.allContent
        case .placeBid: return APIConstant.placeBid
        case .updateBid: return APIConstant.updateBid
        case .myReviews: return APIConstant.myReviews
        case .rateUser: return APIConstant.rateUser
        case .getCategories: return APIConstant.getCategories
        case .jobHistory: return APIConstant.jobHistory
        case .getNotifications: return APIConstant.notifications
        case .startJob: return APIConstant.startJob
        case .getFavouriteList: return APIConstant.getFavouriteList
        case .addToFavourite: return APIConstant.addToFavourite
        case .myJobs: return APIConstant.myJobs
        case .addPortfolio: return APIConstant.addPortfolio
        case .updatePortfolio: return APIConstant.editPortfolio
        case .deletePortfolio: return APIConstant.deletePortfolio
        case .getPortfolio: return APIConstant.getPortfolio
            
        case .addLanguage: return APIConstant.addLanguage
        case .updateLanguage: return APIConstant.updateLanguage
        case .deleteLanguage: return APIConstant.deletePortfolio
        case .getLanguage: return APIConstant.getLanguage
            
        case .addBusinessHour: return APIConstant.addBusinessHour
        case .getBusinessHour: return APIConstant.getBusinessHour
            
        case .addCertificate: return APIConstant.addCertificate
        case .updateCertificate: return APIConstant.editCertificate
        case .deleteCertificate: return APIConstant.deleteCertificate
        case .getCertificates: return APIConstant.getCertificate
            
        case .readNotification: return APIConstant.readNotifications
        case .filterSearch: return APIConstant.filterSearch
        case .userGetProviderDetail:
            return APIConstant.userGetProviderDetail
            //    case respondBid(type: String, post_id: String, provider_id: String)
        case .respondBid: return APIConstant.respondBid
        }
    }
    
    var method: Moya.Method {
        switch self{
        case .logout,.changePassword,.changeAvailibility,.editProfile,.updateBid,.updatePortfolio,.updateLanguage,.updateCertificate: return .put
        case .getHomeListing,.getJobDetails,.getProfile,.allContent,.myReviews,.getCategories,.getNotifications,.jobHistory,.getMapList,.getFavouriteList,.myJobs,.getLanguage,.getPortfolio,.getCertificates,.userJobList, .userGetProviderDetail: return .get
            
        default: return .post
        }
    }
    
    var sampleData: Data {
        return Data("No Data found".utf8)
    }
    
    var task: Task {
        switch self {
        case .signup,.editProfile,.userAddPost, .addToFavourite:
          
            return .uploadMultipart(multipartBody ?? [])
            
        case .getHomeListing,.getJobDetails,.getProfile,.allContent,.myReviews,.getCategories,.getNotifications,.jobHistory,.getMapList,.myJobs,.getFavouriteList,.getCertificates,.getPortfolio,.getLanguage,.userJobList,.userGetProviderDetail:
            
            return .requestParameters(parameters: self.parameters, encoding: URLEncoding.default)
            
         default:
            return .requestParameters(parameters: self.parameters, encoding:JSONEncoding.default)
        }
    }
    
    var headers: [String : String]? {
        
        switch self{
        case .signup,.login,.forgotPassword,.socialLogin,.allContent:
            return AuthHeader.makeAuth()
            
        case .userAddPost,.userJobList:
            return UserHeaders.makeAuth()
            
        case .getCategories(let type):
            return ["type": type,
                    "auth_key": /UserPreference.shared.data?.authKey,
                    "security_key": "JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ="
            ]
            
        case  .logout:
            return ["user_id": "\(/UserPreference.shared.data?.id)",
                "auth_key": /UserPreference.shared.data?.authKey,
                "security_key": "JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ="
            ]
            
        case .getJobDetails(let postId):
            return ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey,"post_id":postId]
        case .getProfile(let providerId):
            return ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey,"provider_id":providerId]
            
        case .readNotification(let type,let notificationId):
            return ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey,"type":type,"notification_id":notificationId]
            
        case .filterSearch(let categoryId,let distance,let state):
            return ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey,"category_id":categoryId,"distance":distance,"state":state]
            
        case .getMapList(_, _,let latitude, let longitude,_):
            return ["security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=","auth_key":/UserPreference.shared.data?.authKey,"latitude":latitude,"longitude":longitude]
            
        case .userGetProviderDetail(let providerId):
            return ["provider_id":providerId,"auth_key":/UserPreference.shared.data?.authKey,"security_key":"JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ="]
            //        case .respondBid(let type, let  post_id, let provider_id):
            //            return [ "type"]
            
        default:
            return UserHeaders.makeAuth()
        }
    }
    
    var parameters:[String:Any]{
        switch self{
        case .signup(let firstName, let lastName, let email, let password, let phone, let country_code, let address,let isNGO, let latitude, let longitude, let age, let socialSecurityNo, let selectedData, let deviceType, let deviceToken, let socialId, _, _, let paypalId,let desc, let socialType):
            if let id = socialId{
                if socialSecurityNo == ""{
                    return ["first_name":firstName,"last_name":lastName,"email":email,"password":password,"phone":phone,"country_code":country_code,"address":address,"latitude":latitude,"longitude":longitude,"age":age,"selected_data":selectedData,"device_type":deviceType,"device_token":deviceToken, "social_id":id,"social_type":socialType, "paypal_id": paypalId,"description":desc,"ngo":isNGO]
                }else{
                    return ["first_name":firstName,"last_name":lastName,"email":email,"password":password,"phone":phone,"country_code":country_code,"address":address,"ngo":isNGO,"latitude" :latitude,"longitude":longitude,"age":age,"social_security_no":socialSecurityNo,"selected_data":selectedData,"device_type":deviceType,"device_token":deviceToken,"social_id":id,"social_type":socialType ,"paypal_id":paypalId, "description":desc]
                }
            }else{
                if socialSecurityNo == ""{
                    return ["first_name":firstName,"last_name":lastName,"email":email,"password":password,"phone":phone,"country_code":country_code,"address":address,"latitude":latitude,"longitude":longitude,"age":age,"selected_data":selectedData,"device_type":deviceType,"device_token":deviceToken,"paypal_id":paypalId,"description":desc]
                }else{
                    return ["first_name":firstName,"last_name":lastName,"email":email,"password":password,"phone":phone,"country_code":country_code,"address":address,"latitude":latitude,"longitude":longitude,"age":age,"social_security_no":socialSecurityNo,"selected_data":selectedData,"device_type":deviceType,"device_token":deviceToken,"paypal_id":paypalId,"description":desc]
                }
            }
            
        case .login(let email, let password, let deviceType, let deviceToken, let latitude, let longitude):
            return ["email":email,"password":password,"device_type":deviceType,"device_token":deviceToken,"latitude":latitude,"longitude":longitude]
            
        case .changePassword(let oldPassword, let newPassword):
            return ["old_password":oldPassword,"new_password":newPassword]
        case .forgotPassword(let email):
            return ["email":email]
        case .socialLogin(let socialId, let socialType, let deviceType, let deviceToken):
            return ["social_id":socialId,"social_type":socialType,"device_type":deviceType,"device_token":deviceToken]
            
        case .userAddPost(let title,let description,let time,let date,let estimationTime,let estimationPrice,let selectedData, let city,let state,let zipCode,let location,let latitude,let longitude,let type,let startPrice, let endPrice ,let startTime, let endTime,let apartmentNo,let jobType, let providerId, _):
            
            
            if let providerId = providerId{
                return ["title":title,"description":description,"time":time,"date":date,"estimationTime":estimationTime,"estimationPrice ":estimationPrice,"selected_data":selectedData,"city":city, "state":state, "zipCode":zipCode,"location":location,"latitude":latitude,"longitude":longitude,"type":type,"startPrice":startPrice, "endPrice":endPrice,"startTime":startTime, "endTime":endTime,"appartment":apartmentNo,"jobType":jobType, "provider_id": providerId]
            }else{
                
                return ["title":title,"description":description,"time":time,"date":date,"estimationTime":estimationTime,"estimationPrice ":estimationPrice,"selected_data":selectedData,"city":city, "state":state, "zipCode":zipCode,"location":location,"latitude":latitude,"longitude":longitude,"type":type,"startPrice":startPrice, "endPrice":endPrice,"startTime":startTime, "endTime":endTime,"appartment":apartmentNo,"jobType":jobType]
            }
            
        case .userJobList(let page ,let limit,let type):
            return ["page":page,"limit":limit,"type":type]
            
        case .getHomeListing(let page, let limit):
            return ["page":page,"limit":limit]
            
         case .respondBid(let type ,let post_id,let provider_id): //(type: String, post_id: String, provider_id: String)
            return ["type":type,"post_id":post_id,"provider_id":provider_id]
            
        case .changeAvailibility(let type):
            return ["type":type]
            
        case .editProfile(let firstName, let lastName, let email, let phone, let countryCode, let description, _, _, _, _, _):
            return ["first_name":firstName,"last_name":lastName,"email":email,"phone":phone,"country_code":countryCode,"description":description]
        case .allContent(let type):
            return ["type":type]
        case .placeBid(let postId, let price):
            return ["post_id":postId,"price":price]
        case .updateBid(let postId, let price):
            return ["post_id":postId,"price":price]
            
        case .rateUser(let userId, let rating, let description):
            return ["userTo":userId,"ratings":rating,"description":description]
        case .jobHistory(let page, let limit):
            return ["page":page,"limit":limit]
            
        case .startJob(let jobId, let status):
            return ["job_id":jobId,"status":status]
        case .getMapList(let page,let limit, let latitude, let longitude, let search):
            return ["page":page,"limit":limit, "latitude": latitude, "longitude": longitude, "search": search]
        case .addToFavourite(let provider_id,let status):
            return ["provider_id":provider_id, "status":status]
        case .myJobs(let page,let limit,let status):
            return ["page":page,"limit":limit,"status":status]
        case .addPortfolio(let title,let description,_):
            return ["title":title,"description":description]
        case .updatePortfolio(let portfolioId,let title,let description,_):
            return ["portfolio_id":portfolioId,"title":title,"description":description]
        case .deletePortfolio(let id):
            return ["portfolio_id":id]
        case .addLanguage(let name,let type):
            return ["name":name,"type":type]
        case .updateLanguage(let langId,let name,let type):
            return ["lang_id":langId,"name":name,"type":type]
        case .deleteLanguage(let langId):
            return ["lang_id":langId]
        case .addCertificate(let title,let description,_):
            return ["title":title,"description":description]
        case .updateCertificate(let certificateId,let title,let description,_):
            return ["certificate_id":certificateId,"title":title,"description":description]
        case .deleteCertificate(let certId):
            return ["certificate_id":certId]
            
            
            
        default:
            return [:]
        }
    }
    
    var multipartBody: [MultipartFormData]? {
        
        switch self {
            
        case .signup(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_, _,let license,let profileImage ,_,_,_):
            var multipartData = [MultipartFormData]()
            
            if let license = license{
                let data = license.jpegData(compressionQuality: 0.3) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(data), name: "driver_license", fileName: "image.jpg", mimeType: "image/jpeg"))
                
            }
            if let profileImg = profileImage{
                let picData = profileImg.jpegData(compressionQuality: 0.3) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(picData), name: "image", fileName: "image.jpg", mimeType: "image/jpeg"))
            }
            
            
            parameters.forEach({ (key, value) in
                let tempValue = "\(value)"
                let data = tempValue.data(using: String.Encoding.utf8) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(data), name: key))
            })
            
            return multipartData
            
        case .editProfile(_, _,_,_,_,_,let image, let businessLicense,let insurance,let resume,let  driverLicense):
            
            var multipartData = [MultipartFormData]()
            
            if let image = image{
                let data = image.jpegData(compressionQuality: 0.3) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(data), name: "image", fileName: "image.jpg", mimeType: "image/jpeg"))
                
            }
            if let businessLicense = businessLicense{
                let picData = businessLicense.jpegData(compressionQuality: 0.3)  ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(picData), name: "businessLicence", fileName: "image.jpg", mimeType: "image/jpeg"))
            }
            
            if let insurance = insurance{
                let picData = insurance.jpegData(compressionQuality: 0.3) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(picData), name: "insurance", fileName: "image.jpg", mimeType: "image/jpeg"))
            }
            
            if let resume = resume{
                let picData = resume.jpegData(compressionQuality: 0.3)  ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(picData), name: "resume", fileName: "image.jpg", mimeType: "image/jpeg"))
            }
            if let license = driverLicense{
                let picData = license.jpegData(compressionQuality: 0.3) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(picData), name: "driverLicence", fileName: "image.jpg", mimeType: "image/jpeg"))
            }
            
            parameters.forEach({ (key, value) in
                let tempValue = "\(value)"
                let data = tempValue.data(using: String.Encoding.utf8) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(data), name: key))
            })
            
            return multipartData
            
        case .userAddPost(_,_,_,_,_,_,_,_,_,_, _, _,_,_,_,_, _, _,_,_,_, let images):
            var multipartData = [MultipartFormData]()
            
            if let images = images{
                for image in images{
                    let picData = image.jpegData(compressionQuality: 0.3) ?? Data()
                    multipartData.append(MultipartFormData.init(provider: .data(picData), name: "image[]", fileName: "image.jpg", mimeType: "image/jpeg"))
                }
            }
            parameters.forEach({ (key, value) in
                let tempValue = "\(value)"
                let data = tempValue.data(using: String.Encoding.utf8) ?? Data()
                multipartData.append(MultipartFormData.init(provider: .data(data), name: key))
            })
                    
            return multipartData
            
            
        case .addToFavourite(_, _):
            var multipartData = [MultipartFormData]()
                       
                        parameters.forEach({ (key, value) in
                           let tempValue = "\(value)"
                           let data = tempValue.data(using: String.Encoding.utf8) ?? Data()
                           multipartData.append(MultipartFormData.init(provider: .data(data), name: key))
                       })
                               
                       return multipartData
       
        default: return nil
        }
        
    }
    
}

