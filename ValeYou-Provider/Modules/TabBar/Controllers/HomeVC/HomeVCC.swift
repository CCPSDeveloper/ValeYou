//
//  HomeVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 01/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import GoogleMaps
import CoreLocation

class HomeVC: UIViewController,CLLocationManagerDelegate {
    
    //MARK: - IBOutlets
    @IBOutlet weak var mapView: GMSMapView!
    @IBOutlet weak var btnList: UIButton!
    @IBOutlet weak var btnMap: UIButton!
    @IBOutlet weak var viewSearch: UIView!
    @IBOutlet weak var cvProviders: UICollectionView!{
        didSet{
            cvProviders.registerXIB(CellIdentifiers.ProviderCell.rawValue)
        }
    }
    @IBOutlet weak var viewTop: UIView!
    @IBOutlet weak var topHeight: NSLayoutConstraint!
    @IBOutlet weak var tblList: UITableView!{
        didSet{
            tblList.registerXIB(CellIdentifiers.ProviderListCell.rawValue)
        }
    }
    @IBOutlet weak var viewList: UIView!
    
    var locationManager:CLLocationManager!
    var cvDataSource:CollectionDataSource?
    var tblDataSource:TableViewDataSource?
    
    var projectList = [ProjectList]()
    
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        getData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func getData(){
        ProviderEP.getMapList(page: "1", limit: "20").request(loader: true, success: { (res) in
            guard let data = res as? ProjectListModel else { return }
            self.projectList = data.data ?? []
            self.configTable()
            self.configCV()
        }) { (error) in
            Toast.shared.showAlert(type: .apiFailure, message: /error)
        }
    }
    
    
    
    func setupView(){
        if Device.IS_IPHONE_X{
                   topHeight.constant = 197
               }else{
                   topHeight.constant = 170
               }
        Utility.dropShadow(mView: viewSearch, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        Utility.dropShadow(mView: viewTop, radius: 2, color: .lightGray, size: CGSize(width: 0, height: 1))
    
        Utility.dropShadow(mView: btnList, radius: 4, color: .lightGray, size: CGSize(width: 0, height: 1))
        
        Utility.dropShadow(mView: btnMap, radius: 4, color: .lightGray, size: CGSize(width: 0, height: 1))
//        configCV()
//        configTable()
        viewList.isHidden = true
        tblList.isHidden = true
    }
    
    
    func configCV(){
        
        cvDataSource = CollectionDataSource(_items: self.projectList, _identifier: CellIdentifiers.ProviderCell.rawValue, _collectionView: cvProviders, _size: CGSize(width: UIScreen.main.bounds.size.width, height: 100), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil)
        
        cvDataSource?.configureCell = { (cell, item, index) in
            guard let mCell = cell as? ProviderCell else { return }
            mCell.item = item
        }
        
        cvDataSource?.didSelectItem = { (index,item) in
            guard let data = item as? ProjectList else { return }
            guard let vc = R.storyboard.details.jobDetailsVC() else { return}
            vc.jobId = /data.id
            Router.shared.pushVC(vc: vc)
        }
        
        cvProviders.delegate = cvDataSource
        cvProviders.dataSource = cvDataSource
    }
    
    func configTable(){
        tblDataSource = TableViewDataSource(items: self.projectList, tableView: tblList, cellIdentifier: CellIdentifiers.ProviderListCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? ProviderListCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            guard let data = item as? ProjectList else { return }
            guard let vc = R.storyboard.details.jobDetailsVC() else { return}
            vc.jobId = /data.id
            Router.shared.pushVC(vc: vc)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        
        tblList.delegate = tblDataSource
        tblList.dataSource = tblDataSource
        tblList.reloadData()
    }
    
    //MARK: - Location Methods
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else {return }
        let latitude = location.coordinate.latitude
        let longitude = location.coordinate.longitude

         let camera = GMSCameraPosition(latitude: latitude, longitude: longitude, zoom: 16)
                mapView.animate(to: camera)
        locationManager.stopUpdatingLocation()
    }
    
    //MARK: - IBAction Methods
    @IBAction func btnActionList(_ sender: Any) {
        viewList.isHidden = false
        tblList.isHidden = false
        btnList.backgroundColor = #colorLiteral(red: 0.3849228024, green: 0.8137173653, blue: 0.956825316, alpha: 1)
        btnMap.backgroundColor = #colorLiteral(red: 0.9219091535, green: 0.9220636487, blue: 0.9218887687, alpha: 1)
        btnList.setTitleColor(.white, for: .normal)
        btnMap.setTitleColor(#colorLiteral(red: 0.3849228024, green: 0.8137173653, blue: 0.956825316, alpha: 1), for: .normal)
        
    }
    
    @IBAction func btnActionMap(_ sender: Any) {
        viewList.isHidden = true
        tblList.isHidden = true
        btnMap.backgroundColor = #colorLiteral(red: 0.3849228024, green: 0.8137173653, blue: 0.956825316, alpha: 1)
        btnList.backgroundColor = #colorLiteral(red: 0.9219091535, green: 0.9220636487, blue: 0.9218887687, alpha: 1)
        btnMap.setTitleColor(.white, for: .normal)
        btnList.setTitleColor(#colorLiteral(red: 0.3849228024, green: 0.8137173653, blue: 0.956825316, alpha: 1), for: .normal)
    }
    
    
    @IBAction func btnActionNotifications(_ sender: Any) {
        
        guard let vc = R.storyboard.sideMenu.notificationsVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    
}
