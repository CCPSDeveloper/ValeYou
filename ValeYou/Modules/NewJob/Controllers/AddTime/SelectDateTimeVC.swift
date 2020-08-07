//
//  SelectDateTimeVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 03/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import FSCalendar

class SelectDateTimeVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var viewStartDate: UIView!
    @IBOutlet weak var viewBack: UIView!
    @IBOutlet weak var viewEndDate: UIView!
    @IBOutlet weak var startDateLbl: UILabel!
    @IBOutlet weak var endDateLbl: UILabel!
    @IBOutlet weak var endDateBtn: UIButton!
    @IBOutlet weak var startDateBtn: UIButton!
var activeDatePickerResponder = UITextField()
    var jobScheduleType : JobScheduleType?
    lazy var datePicker =  UIDatePicker()
    lazy var toolBar = UIToolbar()
      @IBOutlet weak var endDateTF : UITextField!
      @IBOutlet weak var startDateTF : UITextField!
     var selectedStartDate : Date?
     var selectedEndDate : Date?
    @IBOutlet weak var calendarView: FSCalendar!
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView(){
        DispatchQueue.main.async{
            Utility.dropShadow(mView: self.viewBack, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        }
        calendarView.allowsMultipleSelection = false
        calendarView.allowsSelection = false
    }
    
    func setStartDate(_ date: Date){
        print(date)
         startDateLbl.text = date.presentableFormat( lineBreak: true)
    }
    
    func setEndDate(_ date: Date){
        print(date)
        endDateLbl.text = date.presentableFormat( lineBreak: true)
    }
    
    func selectOnCalendar(_ date: Date){
        calendarView.select(date, scrollToDate: true)
//        calendarView.select(self.selectedStartDate, scrollToDate: true)
//        calendarView.select(self.selectedEndDate, scrollToDate: true)
    }
    
    
    //MARK: - IBAction Methods
    @IBAction func btnACtionSave(_ sender: Any) {
        
        if selectedStartDate == nil{
            Toast.shared.showAlert(type: .validationFailure, message: "Please select Start Date and Time")
        }else if selectedEndDate == nil{
            Toast.shared.showAlert(type: .validationFailure, message: "Please select Start Date and Time")
        }else if selectedStartDate! > selectedEndDate!{
            Toast.shared.showAlert(type: .validationFailure, message: "Start date and time should not greater than End date and time.")
        }else{
            //            if jobScheduleType != nil{
            NotificationCenter.default.post(name: .didSelectedDate, object: ["startDate":selectedStartDate!,"endDate":selectedEndDate!,"type":jobScheduleType as Any])
            //            }else{
            //                NotificationCenter.default.post(name: .didSelectedDate, object: ["startDate":selectedStartDate!,"endDate":selectedEndDate!,"type":jobScheduleType])
            //            }
            for vc in self.navigationController!.viewControllers{
                if vc is AddInfoTypeVC{
                    self.navigationController?.popToViewController(vc, animated: true)
                }
            }
            //delete
        }
    }
    
    @IBAction func btnACtionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func startDateBtn(_ sender: Any) {
        //        Utility.shared.p
        
         showDatePicker(txtDatePicker: startDateTF)
    }
    
    @IBAction func endDateBtn(_ sender: Any) {
        showDatePicker(txtDatePicker: endDateTF)
    }
    
}
extension SelectDateTimeVC{
    
    func showDatePicker(txtDatePicker:UITextField){
        activeDatePickerResponder = txtDatePicker
        if jobScheduleType! == .future{
            datePicker.datePickerMode = .dateAndTime
        }else{
            datePicker.datePickerMode = .time
        }
        let calendar = Calendar(identifier: .gregorian)
        let currentDate = Date()
        var components = DateComponents()
        components.calendar = calendar
        _ = calendar.date(byAdding: components, to: currentDate)!
         datePicker.minimumDate = Date()
        //ToolBar
        let toolbar = UIToolbar();
        toolbar.sizeToFit()
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(donedatePicker));
        toolbar.setItems([spaceButton, doneButton], animated: false)
        
        if activeDatePickerResponder == startDateTF{
            startDateTF.inputAccessoryView = toolbar
            startDateTF.inputView = datePicker
            startDateTF.shouldResignOnTouchOutsideMode = .enabled
            startDateTF.becomeFirstResponder()
        }else{
            endDateTF.inputAccessoryView = toolbar
            endDateTF.inputView = datePicker
            endDateTF.shouldResignOnTouchOutsideMode = .enabled
            endDateTF.becomeFirstResponder()
        }
    }
    
    @objc func donedatePicker(txtDatePicker:UITextField){
        
        let date = datePicker.date
        print(date)
        if activeDatePickerResponder == startDateTF{
            if selectedStartDate != nil{
                calendarView.deselect(selectedStartDate!)
            }
            selectedStartDate = date
            setStartDate(date)
            startDateTF.resignFirstResponder()
        }else{
            if selectedEndDate != nil{
                calendarView.deselect(selectedEndDate!)
            }
            selectedEndDate =  date
            setEndDate(date)
            endDateTF.resignFirstResponder()
        }
    }
}

