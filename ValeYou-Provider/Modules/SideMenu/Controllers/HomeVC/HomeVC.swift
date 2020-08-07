////
////  HomeVC.swift
////  ValeYou-Provider
////
////  Created by Pankaj Sharma on 25/05/20.
////  Copyright © 2020 Pankaj Sharma. All rights reserved.
////
//
//import UIKit
//import KYDrawerController
//
//class HomeVC: UIViewController {
//
//    //MARK: - IBOutlets
//    @IBOutlet weak var btnOpenRequest: UIButton!
//    @IBOutlet weak var btnCloseRequest: UIButton!
//    @IBOutlet weak var tblOpenReq: UITableView!{
//        didSet{
//            tblOpenReq.registerXIB(CellIdentifiers.JobCell.rawValue)
//        }
//    }
//    @IBOutlet weak var tblCloseReq: UITableView!{
//        didSet{
//            tblCloseReq.registerXIB(CellIdentifiers.JobCell.rawValue)
//        }
//    }
//    @IBOutlet weak var viewScroll: UIScrollView!
//    @IBOutlet weak var widthStack: NSLayoutConstraint!{
//        didSet{
//            widthStack.constant = 2 * UIScreen.main.bounds.size.width
//        }
//    }
//    @IBOutlet weak var lblLine: UILabel!
//
//    //MARK: - Properties
//    var shouldRunScroll = true
//     var selectedTab = 0
//    var openDataSource:TableViewDataSource?
//    var closeDataSource:TableViewDataSource?
//
//
//    //MARK: - Life Cycle Methods
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        viewScroll.delegate = self
//        configureOpenTbl()
//        configureCloseTbl()
//        NotificationCenter.default.addObserver(self, selector: #selector(closeDrawer), name: Notification.Name("CloseDrawer"), object: nil)
//    }
//
//    func configureOpenTbl(){
//        openDataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblOpenReq, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
//            guard let mCell = cell as? JobCell else { return }
//            mCell.item = item
//        }, aRowSelectedListener: { (index, item) in
//            guard let vc = R.storyboard.details.startJobVC() else { return }
//                       Router.shared.pushVC(vc: vc)
//        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
//        tblOpenReq.delegate = openDataSource
//                       tblOpenReq.dataSource = openDataSource
//                       tblOpenReq.reloadData()
//    }
//
//    @objc func closeDrawer(){
//        if let vc = self.navigationController?.parent as? KYDrawerController{
//                        vc.setDrawerState(.closed, animated: true)
//                    }
//    }
//
//    func configureCloseTbl(){
//           closeDataSource = TableViewDataSource(items: [1,2,3,4,3,3,3,3,3,3], tableView: tblCloseReq, cellIdentifier: CellIdentifiers.JobCell.rawValue, configureCellBlock: { (cell, item, index) in
//               guard let mCell = cell as? JobCell else { return }
//               mCell.item = item
//           }, aRowSelectedListener: { (index, item) in
//            guard let vc = R.storyboard.details.jobDetailsVC() else { return }
//            Router.shared.pushVC(vc: vc)
//           }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
//        tblCloseReq.delegate = closeDataSource
//                          tblCloseReq.dataSource = closeDataSource
//                          tblCloseReq.reloadData()
//       }
//
//    @IBAction func btnActionCloseRequest(_ sender: Any) {
//        shouldRunScroll = false
//               setTab(index: 1)
//               viewScroll.scrollRectToVisible(tblCloseReq.frame, animated: true)
//    }
//
//    @IBAction func btnActionOpenRequest(_ sender: Any) {
//        shouldRunScroll = false
//               setTab(index: 1)
//               viewScroll.scrollRectToVisible(tblOpenReq.frame, animated: true)
//    }
//
//    @IBAction func btnActionMenu(_ sender: Any) {
//        if let vc = self.navigationController?.parent as? KYDrawerController{
//                  vc.setDrawerState(.opened, animated: true)
//              }
//    }
//    @IBAction func btnActionNotifications(_ sender: Any) {
//    }
//
//    
//    func setTab(index:Int){
//        selectedTab = index
//        btnOpenRequest.setTitleColor(.lightGray, for: .normal)
//        btnCloseRequest.setTitleColor(.lightGray, for: .normal)
//        switch index{
//        case 0:
//
//            let leftTransform = self.lblLine.transform.translatedBy(x: 0 - lblLine.frame.origin.x, y: 0)
//            btnOpenRequest.setTitleColor(#colorLiteral(red: 0.1362808645, green: 0.5730051398, blue: 0.724821806, alpha: 1), for: .normal)
//            UIView.animate(withDuration: 0.2) {
//                self.lblLine.transform = leftTransform
//            }
//
//        case 1:
//
//            btnCloseRequest.setTitleColor(#colorLiteral(red: 0.1362808645, green: 0.5730051398, blue: 0.724821806, alpha: 1), for: .normal)
//            let rightTransform = self.lblLine.transform.translatedBy(x: UIScreen.main.bounds.size.width/2 - lblLine.frame.origin.x, y: 0)
//            UIView.animate(withDuration: 0.2) {
//                self.lblLine.transform = rightTransform
//            }
//        default:
//            break
//        }
//    }
//
//
//}
//extension HomeVC:UIScrollViewDelegate{
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
