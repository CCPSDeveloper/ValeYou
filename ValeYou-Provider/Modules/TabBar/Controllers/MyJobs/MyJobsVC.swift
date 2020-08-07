//
//  MyJobsVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class MyJobsVC: UIViewController {
    
    
    @IBOutlet weak var optionBack: UIView!
    @IBOutlet weak var viewOption: UIView!
    @IBOutlet weak var lblLine: UILabel!
    @IBOutlet weak var widthStack: NSLayoutConstraint!{
        didSet{
            widthStack.constant = 1 * UIScreen.main.bounds.size.width
        }
    }
    @IBOutlet weak var tblCurrent: UITableView!{
        didSet{
            tblCurrent.registerXIB(CellIdentifiers.JobCell.rawValue)
        }
    }
    @IBOutlet weak var tblPast: UITableView!{
        didSet{
            tblPast.registerXIB(CellIdentifiers.JobCell.rawValue)
        }
    }
    
    @IBOutlet weak var viewScroll: UIScrollView!
    
    //MARK: - Properties
    var shouldRunScroll = true
    var selectedTab = 0
    
    var currentDataSource:TableViewDataSource?
    var pastDataSource:TableViewDataSource?
//    var items =
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        //viewScroll.delegate = self
        setupView()
        configureCurrentTbl()
        getJobs(status:0)
        // configurePastTbl()
     }
 
    func setupView(){
        optionBack.isHidden = true
        viewOption.isHidden = true
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(optionOutsideTapped))
        optionBack.isUserInteractionEnabled = true
        optionBack.addGestureRecognizer(tapGesture)
        Utility.dropShadow(mView: viewOption, radius: 3, color: .lightGray, size: CGSize(width: 1, height: 1))
    }
    
    func getJobs(status:Int){
        ProviderEP.myJobs(page: "1", limit: "20", status: "\(status)").request(loader: true, success: { (res) in
            
//            guard let data = item as? ProjectList else { return }
            self.configureCurrentTbl()
            
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    @objc func optionOutsideTapped(){
        viewOption.isHidden = true
        optionBack.isHidden = true
    }
 
    func configureCurrentTbl(){
        currentDataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblCurrent, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? JobCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            guard let vc = R.storyboard.details.jobDetailsVC() else { return }
            Router.shared.pushVC(vc: vc)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblCurrent.delegate = currentDataSource
        tblCurrent.dataSource = currentDataSource
        tblCurrent.reloadData()
    }
    
    func configurePastTbl(){
        pastDataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblPast, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? JobCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblPast.delegate = pastDataSource
        tblPast.dataSource = pastDataSource
        tblPast.reloadData()
    }
     
    @IBAction func btnActionAdd(_ sender: Any) {
        //        guard let vc = R.storyboard.newJob.selectCategoryVC() else { return }
        //        Router.shared.pushVC(vc: vc)
    }
    
    
    @IBAction func btnActionFilter(_ sender: Any) {
        viewOption.isHidden = false
        optionBack.isHidden = false
    }
    
    @IBAction func btnActionAll(_ sender: Any) {
    }
    
    
    @IBAction func btnActionCompleted(_ sender: Any) {
    }
    
    
    @IBAction func btnActionOngoing(_ sender: Any) {
    }
    
    @IBAction func btnActionUpcoming(_ sender: Any) {
    }
    
    @IBAction func btnActionCancelled(_ sender: Any) {
    }
}

//extension MyJobsVC:UIScrollViewDelegate{
//
//    func scrollViewDidScroll(_ scrollView: UIScrollView) {
//        if scrollView == viewScroll{
//            if shouldRunScroll{
//                let index = Int(scrollView.contentOffset.x/UIScreen.main.bounds.size.width)
//                setTab(index: index)
//            }
//        }
//    }
//
//    func scrollViewDidEndScrollingAnimation(_ scrollView: UIScrollView) {
//        shouldRunScroll = true
//    }
//}
