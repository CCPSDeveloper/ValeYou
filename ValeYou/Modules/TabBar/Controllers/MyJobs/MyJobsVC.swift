//
//  MyJobsVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

public enum UserJobType : Int{
    case complete = 4
    case ongoing = 3
    case cancelled = 2
    case upcoming = 1
    case all = 0
    
    /* 4=complete,
     3=ongoing,
     2 canceled,
     1= upcoming
     0=all
     */
    
}

public typealias JobTypeAttributes = (title: String,color: UIColor)

public func getJobTypeName(with type : UserJobType) -> JobTypeAttributes{
    switch type{
    case .all:
        return ("All",.purple)
    case .cancelled:
        return ("Cancelled",.red)
    case .complete:
        return ("Completed",.green)
    case .upcoming:
        return ("Upcoming",#colorLiteral(red: 0, green: 0.6558215022, blue: 0.9967903495, alpha: 1))
    case .ongoing:
        return ("Ongoing",.orange)
    }
}

class MyJobsVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var btnCurrent: UIButton!
    @IBOutlet weak var btnPastJob: UIButton!
    @IBOutlet weak var lblLine: UILabel!
    @IBOutlet weak var filterView: UIView!
    @IBOutlet weak var filterOutsideTapArea: UIView!
    @IBOutlet weak var widthStack: NSLayoutConstraint!{
        didSet{
            widthStack.constant = 2 * UIScreen.main.bounds.size.width
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
    
    
    //MARK: - Life Cycle Methodsl;
    override func viewDidLoad() {
        super.viewDidLoad()
        viewScroll.delegate = self
        self.filterOutsideTapArea.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(filterOutsideTapGesture(sender:))))
        self.filterView(true)
        configurePastTbl()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        getJobs(pageNo: 1, type: .all)
    }
    
    var jobs = [UserJob](){
        willSet{
            reloadData()
        }
    }
    
    func  reloadData(){
        self.tblCurrent.reloadData()
    }
    
    
    func configureCurrentTbl(){
        currentDataSource = TableViewDataSource(items: jobs, tableView: tblCurrent, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? JobCell else { return }
            //            mCell.item = item
            let item = self.jobs[index!.row]
            mCell.lblAddress.text = item.location
            mCell.lblJobName.text = item.title
            let startTime = Double(item.startTime)!
            let startTimeDate = Date(timeIntervalSince1970: startTime)
            mCell.lblTime.text = startTimeDate.presentableFormat()
            let attributes = getJobTypeName(with: UserJobType(rawValue: item.jobType)!)
            mCell.jobStatusLbl.text = attributes.title
            mCell.btnPlaceBid.setTitle(attributes.title, for: .normal)
            mCell.btnPlaceBid.isHidden = false
            mCell.btnPlaceBid.backgroundColor = attributes.color
            
        }, aRowSelectedListener: { (index, item) in
            guard let vc = R.storyboard.details.jobDetailsVC() else { return }
            vc.job = self.jobs[index.row]
            Router.shared.pushVC(vc: vc)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblCurrent.delegate = currentDataSource
        tblCurrent.dataSource = currentDataSource
        tblCurrent.reloadData()
        if self.jobs.count < 0{
            self.tblCurrent.setEmptyMessage("No \(getJobTypeName(with: self.selectedType).title) found.")
        }else{
            self.tblCurrent.restore()
        }
    }
    
    var selectedType : UserJobType = .all
    func configurePastTbl(){
        pastDataSource = TableViewDataSource(items: jobs, tableView: tblPast, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? JobCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblPast.delegate = pastDataSource
        tblPast.dataSource = pastDataSource
        tblPast.reloadData()
    }
    
    func filterView(_ isHidden: Bool){
        filterView.isHidden = isHidden
        self.filterOutsideTapArea.isHidden = isHidden
    }
    
    @objc func filterOutsideTapGesture(sender: UITapGestureRecognizer){
        filterView(true)
    }
    
    //MARK: - IBAction Methods
    
    @IBAction func btnActionFilter(_ sender: Any) {
        filterView(false)
    }
    
    @IBAction func btnActionPast(_ sender: Any) {
        shouldRunScroll = false
        setTab(index: 1)
        viewScroll.scrollRectToVisible(tblPast.frame, animated: true)
    }
    
    @IBAction func btnActionCurrent(_ sender: Any) {
        shouldRunScroll = false
        setTab(index: 0)
        viewScroll.scrollRectToVisible(tblCurrent.frame, animated: true)
    }
    
    @IBAction func btnActionAdd(_ sender: Any) {
        guard let vc = R.storyboard.newJob.selectCategoryVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    @IBAction func btnActionAll(_ sender: Any) {
        
    }
    
    @IBAction func btnActionCompleted(_ sender: Any){
        
    }
    
    @IBAction func filterTypeSelectedACtion(_ sender: Any) {
        let type = UserJobType(rawValue: ((sender as! UIButton).tag)/1000)
        self.selectedType = type!
        switch type {
        case .all:
            getJobs(pageNo: 1, type: .all)
            
        case .complete:
            getJobs(pageNo: 1, type: .complete)
            
        case .ongoing :
            getJobs(pageNo: 1, type: .ongoing)
            
        case .cancelled:
            getJobs(pageNo: 1, type: .cancelled)
            
        case .upcoming:
            getJobs(pageNo: 1, type: .upcoming)
            
        case .none:
            break
        }
        self.filterView(true)
    }
    
    func setTab(index:Int){
        selectedTab = index
        btnCurrent.setTitleColor(.lightGray, for: .normal)
        btnPastJob.setTitleColor(.lightGray, for: .normal)
        switch index{
        case 0:
            let leftTransform = self.lblLine.transform.translatedBy(x: 0 - lblLine.frame.origin.x, y: 0)
            btnCurrent.setTitleColor(#colorLiteral(red: 0.1362808645, green: 0.5730051398, blue: 0.724821806, alpha: 1), for: .normal)
            UIView.animate(withDuration: 0.2) {
                self.lblLine.transform = leftTransform
            }
        case 1:
            btnPastJob.setTitleColor(#colorLiteral(red: 0.1362808645, green: 0.5730051398, blue: 0.724821806, alpha: 1), for: .normal)
            let rightTransform = self.lblLine.transform.translatedBy(x: UIScreen.main.bounds.size.width/2 - lblLine.frame.origin.x, y: 0)
            UIView.animate(withDuration: 0.2) {
                self.lblLine.transform = rightTransform
            }
        default:
            break
        }
    }
    
}

extension MyJobsVC:UIScrollViewDelegate{
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == viewScroll{
            if shouldRunScroll{
                let index = Int(scrollView.contentOffset.x/UIScreen.main.bounds.size.width)
                setTab(index: index)
            }
        }
    }
    
    func scrollViewDidEndScrollingAnimation(_ scrollView: UIScrollView) {
        shouldRunScroll = true
    }
}
