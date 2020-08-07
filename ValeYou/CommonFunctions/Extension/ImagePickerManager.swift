//
//  ImagePickerManager.swift
//  Guild
//
//  Created by Techwin on 27/02/20.
//  Copyright © 2020 Isaac Adegbenro . All rights reserved.
//

import Foundation
 import UIKit


class ImagePickerManager: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    var picker = UIImagePickerController();
    //    var alert = UIAlertController(title: "Choose Image", message: nil, preferredStyle: .actionSheet)
    var viewController: UIViewController?
    var pickImageCallback : ((UIImage?,String?) -> ())?;
    
    static let shared = ImagePickerManager()
    
    override init(){
        super.init()
    }
    
    func pickImage(_ viewController: UIViewController, source: UIImagePickerController.SourceType ,  _ callback: @escaping ((UIImage?,String?) -> ())) {
        pickImageCallback = callback
        self.viewController = viewController
        switch source{
        case .camera:
            //            self.openCamera()
            if(UIImagePickerController .isSourceTypeAvailable(.camera)){
                picker.sourceType = .camera
                self.viewController!.present(picker, animated: true, completion: nil)
            } else {
                
            }
            
        case .photoLibrary:
            picker.sourceType = .photoLibrary
            self.viewController!.present(picker, animated: true, completion: nil)
            
        case .savedPhotosAlbum:
            picker.sourceType = .savedPhotosAlbum
            self.viewController!.present(picker, animated: true, completion: nil)
        }
        
        // Add the actions
        picker.delegate = self
        
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
 
    
    // For Swift 4.2+
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        picker.dismiss(animated: true, completion: nil)
        guard let image = info[.originalImage] as? UIImage else {
            pickImageCallback?(nil,"Expected a dictionary containing an image, but was provided the following: \(info)")
            fatalError("Expected a dictionary containing an image, but was provided the following: \(info)")
        }
        pickImageCallback?(image, nil)
    }
    
    @objc func imagePickerController(_ picker: UIImagePickerController, pickedImage: UIImage?) {
        
    }
    
}
