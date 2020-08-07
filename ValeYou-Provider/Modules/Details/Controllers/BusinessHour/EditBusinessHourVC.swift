//
//  EditBusinessHourVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 29/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class EditBusinessHourVC: UIViewController {
    
    //MARK: - IBOutlets
    
    @IBOutlet weak var viewBack: UIView!
    
    
    @IBOutlet var expandView: [UIView]!
    
    
    @IBOutlet weak var heightExpand1: NSLayoutConstraint!
    @IBOutlet weak var heightExpand2: NSLayoutConstraint!
    @IBOutlet weak var heightExpand3: NSLayoutConstraint!
    @IBOutlet weak var heightExpand4: NSLayoutConstraint!
    @IBOutlet weak var heightExpand5: NSLayoutConstraint!
    @IBOutlet weak var heightExpand6: NSLayoutConstraint!
    @IBOutlet weak var heightExpand7: NSLayoutConstraint!
    @IBOutlet var lblDayTime: [UILabel]!
    @IBOutlet var viewTime: [UIView]!
    
    @IBOutlet  var dropImg: [UIImageView]!
    
    @IBOutlet var viewSelectTime: [UIView]!
    
    @IBOutlet  var imgCheck: [UIImageView]!
    
    @IBOutlet var lblStartTimeAM: [UILabel]!
    
    @IBOutlet var lblStartTimeHour: [UILabel]!
    
    @IBOutlet var lblStartTimeMinute: [UILabel]!
    
    
    @IBOutlet var lblEndTimeAM: [UILabel]!
    
    @IBOutlet var lblEndTimeHour: [UILabel]!
    
    @IBOutlet var lblEndTimeMinute: [UILabel]!
    
    var previousSelection = -1
    
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        
        for v in viewTime{
            Utility.dropShadow(mView: v, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
        }
        
        for v in viewSelectTime{
            Utility.dropShadow(mView: v, radius: 2, color: .lightGray, size: CGSize(width: 1, height: 1))
        }
        
        setExpandView(index: 9)

    }
    
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionDone(_ sender: Any) {
    }
    
    
    @IBAction func btnActionExpand(_ sender: Any) {
        var tag = (sender as? UIButton)?.tag
        if tag == previousSelection{
            tag = -5
        }
        setExpandView(index: tag ?? 0)
    }
    
    @IBAction func btnActionSetOnOff(_ sender: Any) {
        let tag = (sender as? UIButton)?.tag
        for i in 0..<imgCheck.count{
            if i == tag{
                
            }
        }
    }
    
    
    @IBAction func btnActionPickStartTime(_ sender: Any) {
    }
    
    @IBAction func btnActionPickEndTime(_ sender: Any) {
    }

    
    func setExpandView(index:Int){
        previousSelection = index
        for v in expandView{
            v.isHidden = true
        }
        heightExpand1.constant = 0
        heightExpand2.constant = 0
        heightExpand3.constant = 0
        heightExpand4.constant = 0
        heightExpand5.constant = 0
        heightExpand6.constant = 0
        heightExpand7.constant = 0
        for v in expandView{
            if v.tag == index{
                v.isHidden = false
            }
        }
        switch index{
        case 0:
            heightExpand1.constant = 80
        case 1:
            heightExpand2.constant = 80
        case 2:
            heightExpand3.constant = 80
        case 3:
            heightExpand4.constant = 80
        case 4:
            heightExpand5.constant = 80
        case 5:
            heightExpand6.constant = 80
        case 6:
            heightExpand7.constant = 80
        default: break
        }
    }
    
}
