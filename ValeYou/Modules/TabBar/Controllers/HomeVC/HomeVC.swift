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
import KYDrawerController

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
    var items = [GetProviderListData]()
    var locationManager:CLLocationManager!
    var cvDataSource:CollectionDataSource?
    var tblDataSource:TableViewDataSource?
    
    var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.attributedTitle = NSAttributedString(string: "Refreshing...")
        refreshControl.backgroundColor = UIColor.clear //.withAlphaComponent(0.2)
        refreshControl.tintColor = UIColor.black
        refreshControl.addTarget(self, action: #selector(refresh(sender:)), for: .valueChanged)
        return refreshControl
    }()
    
    @IBOutlet weak var searchTf: UITextField!
    //MARK: - Life Cycle Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
    }
        
    //MARK:- PULL TO REFRESH
    @objc func refresh(sender:AnyObject){
        self.getData()
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
        configCV()
        configTable()
        viewList.isHidden = true
        //        tblList.isHidden = true
        self.cvProviders.refreshControl = self.refreshControl
        self.tblList.refreshControl = self.refreshControl
        self.menuBtn.cornerRadius = self.menuBtn.frame.size.height / 2
        self.searchTf.delegate = self
    }
 
    func configCV(){
        cvDataSource = CollectionDataSource(_items: items, _identifier: CellIdentifiers.ProviderCell.rawValue, _collectionView: cvProviders, _size: CGSize(width: UIScreen.main.bounds.size.width, height: 100), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil, configureCell: { (cell, item, indexPath) in
            guard let cell = cell as? ProviderCell else { return }
            cell.item = item
            
        }, didSelectedItem: { (indexPath, item) in
            guard let indexPath = indexPath as? IndexPath,
                let item = item as? GetProviderListData
                else { return }
            self.pushToProfile(data: item)
        })
        
        cvProviders.delegate = cvDataSource
        cvProviders.dataSource = cvDataSource
        cvProviders.reloadData()
    }
    
    func configTable(){
        tblDataSource = TableViewDataSource(items: items, tableView: tblList, cellIdentifier: CellIdentifiers.ProviderListCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let mCell = cell as? ProviderListCell else { return }
            mCell.item = item
        }, aRowSelectedListener: { (index, item) in
            guard let indexPath = index as? IndexPath,
                let item = item as? GetProviderListData
                else { return }
            self.pushToProfile(data: item)
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        
        tblList.delegate = tblDataSource
        tblList.dataSource = tblDataSource
        tblList.reloadData()
    }
    
    
    func pushToProfile(data: GetProviderListData){
        let vc = R.storyboard.details.providerProfileVC()!
        vc.providerData = data
        Router.shared.pushVC(vc: vc)
    }
    
    //MARK: - Location Methods
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else {return }
        let latitude = location.coordinate.latitude
        let longitude = location.coordinate.longitude
     
        lat = latitude
        long = longitude
 
        let camera = GMSCameraPosition(latitude: latitude, longitude: longitude, zoom: 16)
        mapView.animate(to: camera)
        getData()
        locationManager.stopUpdatingLocation()
    }
    
    var lat: Double?
    var long: Double?
    
    func getData(searchText : String? = nil){
        
        guard let lat = lat , let long = long else {
            
            Toast.shared.showAlert(type: .validationFailure, message: "Please check if location services is enable for the app to fetch location accurate data.")
            return
        }
        
        UserEP.getMapList(page: "1", limit: "20", latitude: "\(lat)", longitude: "\(long)", search: searchText ?? "").request(loader: searchText == nil ? true : false, success: { (res) in
            guard let res = res as? GetProviderList else { return }
            self.items = res.data
            self.addMarkersOnMap(users: self.items)
            self.configCV()
            self.configTable()
        }) { (error) in
            guard let error = error else { return }
            Toast.shared.showAlert(type: .apiFailure, message: error)
        }
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
    
    @IBOutlet weak var menuBtn: UIButton!
    
    @IBAction func menuBtn(_ sender: Any) {
        Router.shared.drawer?.setDrawerState(.opened, animated: true)
    }
    
    @IBAction func btnActionNotifications(_ sender: Any) {
        
        guard let vc = R.storyboard.sideMenu.notificationsVC() else { return }
        Router.shared.pushVC(vc: vc)
    }
    
    
}

extension HomeVC: UITextFieldDelegate{
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
       
        let typeCasteToStringFirst = textField.text as NSString?
        let mString = typeCasteToStringFirst?.replacingCharacters(in:range, with: string)
        self.changedCharacter(searchText: mString!)
        return true
    }
    
//    var filteredArray = NSArray()
//    var reservedArray = NSMutableArray()
    
    func changedCharacter(searchText: String) {
        /*
         //PREDICATE
         if searchText.count > 0{
         let bPredicate: NSPredicate = NSPredicate(format: "SELF.user_name contains[cd] %@", searchText)
         print("resultPredicate  \(bPredicate)")
         let t = reservedArray.filtered(using: bPredicate)
         let filteredArray2  = NSMutableArray(array: t)
         appointmentsArray = filteredArray2
         }else{
         appointmentsArray = reservedArray
         }
         self.tableView.reloadData()
         */
        
        if searchText.count > 0{
            self.getData(searchText: searchText)
        }else{ // SEARCH IS OVER
            getData()
        }
    }

    
}
