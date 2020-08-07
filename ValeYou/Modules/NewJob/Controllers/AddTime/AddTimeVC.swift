//
//  AddTimeVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

enum JobScheduleType : String{
    case now = "1"
    case today = "2"
    case future = "3"
}

protocol AddTimeVCDelegate {
    func didSelected(type: JobScheduleType, date: Date, time: Date)
}

class AddTimeVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewNow: UIView!
    @IBOutlet weak var viewToday: UIView!
    @IBOutlet weak var viewFuture: UIView!
    var jobScheduleType : JobScheduleType = .now
    var delegate : AddTimeVCDelegate?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()

        setupView()
    }
    
    func setupView(){
        Utility.dropShadow(mView: viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewNow, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewToday, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewFuture, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
    }

    //MARK: - IBAction Methods
    @IBAction func btnActionNow(_ sender: Any) {
        jobScheduleType = .now
//        delegate?.didSelected(type: jobScheduleType, date: Date(), time: Date())
        let calendar = Calendar(identifier: .gregorian)
        let selectedStartDate = Date()
        var components = DateComponents()
        components.hour = 2
        let selectedEndDate = calendar.date(byAdding: components, to: selectedStartDate)

        NotificationCenter.default.post(name: .didSelectedDate, object: ["startDate":selectedStartDate,"endDate":selectedEndDate as Any,"type":JobScheduleType.now as Any])
        Router.shared.popFromInitialNav()
 
     }
    
    @IBAction func btnActionToday(_ sender: Any) {
        jobScheduleType = .today
        guard let vc = R.storyboard.newJob.selectDateTimeVC() else { return }
        vc.jobScheduleType = JobScheduleType.today
        Router.shared.pushVC(vc: vc)
        //        delegate?.didSelected(type: jobScheduleType, date: Date(), time: Date())
        //        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionFuture(_ sender: Any) {
        guard let vc = R.storyboard.newJob.selectDateTimeVC() else { return }
        vc.jobScheduleType = JobScheduleType.future
        Router.shared.pushVC(vc: vc)
 
    }
    
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
}
