//
//  OnboardingVC.swift
//  Pynkiwi
//
//  Created by Pankaj Sharma on 08/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class OnboardingVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var cvOnboarding: UICollectionView!{
        didSet{
            cvOnboarding.registerXIB(CellIdentifiers.OnboardingCell.rawValue)
        }
    }
    @IBOutlet weak var dot1: UIImageView!
    @IBOutlet weak var dot2: UIImageView!
    @IBOutlet weak var dot3: UIImageView!
   
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var lblSubtitle: UILabel!
    @IBOutlet weak var btnNext: UIButton!
    
    
    //MARK: - Properties
    var dataSource:CollectionDataSource?
    var images = [#imageLiteral(resourceName: "art-1.png"),#imageLiteral(resourceName: "art-2.png"),#imageLiteral(resourceName: "art-3.png")]
    var titleArray = ["tutorial1".localize,"tutorial2".localize,"tutorial3".localize,"tutorial3".localize]
    var currentIndex = 0
    var orangeDot = #imageLiteral(resourceName: "dots-black.png")
    var whiteDot = #imageLiteral(resourceName: "dots.gray.png")
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        btnNext.setTitle("next".localize, for: .normal)
        setDots(index: currentIndex)
      //  self.lblTitle.text = self.titleArray[currentIndex]
        configureCollection()
    }
    
    func configureCollection(){
        dataSource = CollectionDataSource(_items: images, _identifier: CellIdentifiers.OnboardingCell.rawValue, _collectionView: cvOnboarding, _size: CGSize(width: UIScreen.main.bounds.size.width, height: cvOnboarding.frame.size.height), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil)
        
        dataSource?.configureCell = { (cell,item,index) in
            guard let mCell = cell as? OnboardingCell else { return }

            mCell.item = item
        }
        
        dataSource?.didScroll = {
            [weak self] in
            let index = Int((self?.cvOnboarding.contentOffset.x ?? 0.0)/UIScreen.main.bounds.size.width)
          //  self?.lblTitle.text = self?.titleArray[index]
            self?.currentIndex = index
            self?.setDots(index: index)
            if self?.currentIndex == 2{
                self?.btnNext.setTitle("getStart".localize, for: .normal)
            }else{
                self?.btnNext.setTitle("next".localize, for: .normal)
            }
        }
    }
    
    
    //MARK:IBAction Methods
    @IBAction func btnActionNext(_ sender: Any) {
        if currentIndex == 2{
            guard let vc = R.storyboard.authentication.loginVC() else { return }
            Router.shared.pushVC(vc: vc)
        }else{
            currentIndex += 1
            cvOnboarding.scrollRectToVisible(CGRect(x: 0, y: 0, width: CGFloat(currentIndex + 1) * UIScreen.main.bounds.size.width, height: cvOnboarding.frame.size.height), animated: true)
        }
    }
    

    
    func setDots(index:Int){
        dot1.image = whiteDot
        dot2.image = whiteDot
        dot3.image = whiteDot
        
        switch index{
        case 0:
            dot1.image = orangeDot
        case 1:
            dot2.image = orangeDot
        case 2:
            dot3.image = orangeDot
        
        default:
            break
        }
    }
}
