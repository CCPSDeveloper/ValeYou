//
//  TabVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class TabVC: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()
        let middleButton = UIButton()
        middleButton.frame.size = CGSize(width: 80, height: 80)
        middleButton.backgroundColor = .clear
        middleButton.layer.cornerRadius = 40
        middleButton.layer.masksToBounds = true
        if Device.IS_IPHONE_X{
            middleButton.center = CGPoint(x: UIScreen.main.bounds.width / 2, y:
                   UIScreen.main.bounds.height - 80)
        }else{
            middleButton.center = CGPoint(x: UIScreen.main.bounds.width / 2, y:
                   UIScreen.main.bounds.height - 50)
        }
       
        view.addSubview(middleButton)
        middleButton.addTarget(self, action: #selector(homeTapped), for: .touchUpInside)
        selectedIndex = 2
    }
    
    @objc func homeTapped(_ sender:UIButton){
        selectedIndex = 2
    }
  

}
