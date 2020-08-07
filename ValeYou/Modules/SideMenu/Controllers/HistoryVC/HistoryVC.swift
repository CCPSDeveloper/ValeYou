//
//  HistoryVC.swift
//  ValeYou-Provider
//
//  Created by Pankaj Sharma on 25/05/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import KYDrawerController

class HistoryVC: UIViewController {
    
    //MARK: - IBOutlets
    @IBOutlet weak var btnCurrent: UIButton!
    
    @IBOutlet weak var btnPastJob: UIButton!
    @IBOutlet weak var lblLine: UILabel!
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
    var shouldRunScroll = true
    var selectedTab = 0
    
    var currentDataSource:TableViewDataSource?
    var pastDataSource:TableViewDataSource?
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        viewScroll.delegate = self
        configureCurrentTbl()
        configurePastTbl()
        NotificationCenter.default.addObserver(self, selector: #selector(closeDrawer), name: Notification.Name("CloseDrawer"), object: nil)
    }
    
    @objc func closeDrawer(){
         if let vc = self.navigationController?.parent as? KYDrawerController{
                         vc.setDrawerState(.closed, animated: true)
                     }
     }
    
    func configureCurrentTbl(){
           currentDataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblCurrent, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
               guard let mCell = cell as? JobCell else { return }
               mCell.item = item
           }, aRowSelectedListener: { (index, item) in
               guard let vc = R.storyboard.details.currentJobVC() else { return }
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
       
    
    //MARK: - IBAction Methods
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
    
    @IBAction func btnActionMenu(_ sender: Any) {
        if let vc = self.navigationController?.parent as? KYDrawerController{
            vc.setDrawerState(.opened, animated: true)
        }
    }
    
    @IBAction func btnActionNotification(_ sender: Any) {
        
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

extension HistoryVC:UIScrollViewDelegate{
    
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
