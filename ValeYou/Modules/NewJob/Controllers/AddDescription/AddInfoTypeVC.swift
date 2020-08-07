//
//  AddInfoTypeVC.swift
//  ValeYou
//
//  Created by Pankaj Sharma on 02/06/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import RangeSeekSlider
import CoreData
import CoreLocation
class AddInfoTypeVC: UIViewController {
    
    @IBOutlet weak var viewBack: UIView!
    
    @IBOutlet weak var tblSub: UITableView!{
        didSet{
            tblSub.registerXIB(CellIdentifiers.JobInfoTypeCell.rawValue)
        }
    }
    
    var selectedImages = [UIImage]()

    @IBOutlet weak var selectedSubCategoriesLbl: UILabel!
    @IBOutlet weak var lblTitle: UILabel!
//    @IBOutlet weak var sliderBase: UIView!
//    @IBOutlet weak var sliderLbl: UILabel!
//    @IBOutlet weak var slider: RangeSeekSlider!
    var selectedMinPrice = CGFloat()
    var selectedMaxPrice = CGFloat()
    
    var dateFmtr : DateFormatter{
        let ft = DateFormatter()
        ft.dateFormat = "dd/MM/yyyy, h:mm a"
        return ft
    }
    
    let selectPicturesView = SelectPicturesView(frame: UIScreen.main.bounds)
 
    //MARK: - Properties
    var screenTitle = ""
    var tvDataSource:TableViewDataSource?
    var postData = PostData()
    
    private struct JobInfoTypeData {
        var itemTitle : String
        var itemIcon : UIImage
        var selectedData: String?
        var photos: [UIImage]?
    }
    
    private var items : [JobInfoTypeData] = [
        JobInfoTypeData(itemTitle: "Where?",itemIcon: #imageLiteral(resourceName: "where-icon"),selectedData: nil,photos: nil),
        JobInfoTypeData(itemTitle: "When?",itemIcon: #imageLiteral(resourceName: "calendar-icon-1"),selectedData: nil,photos: nil),
        JobInfoTypeData(itemTitle: "Photo?",itemIcon: #imageLiteral(resourceName: "Photo-icon-1"),selectedData: nil,photos: nil),
        JobInfoTypeData(itemTitle: "Estimated Price",itemIcon: #imageLiteral(resourceName: "privacy-icon-1"),selectedData: nil,photos: nil)
    ]
    
//    typealias jobPhotos =
  
    //MARK: - Life Cycle Methods
    override func viewDidLoad(){
        super.viewDidLoad()
        setupView()
        print(postData as Any)
    }
    
    func setupView(){
        //        selectedMinPrice = slider.selectedMinValue
        //        selectedMaxPrice = slider.selectedMaxValue
        lblTitle.text = screenTitle
        Utility.dropShadow(mView: viewBack ,radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        //        Utility.dropShadow(mView: sliderBase, radius: 4, color: .lightGray, size: CGSize(width: 1, height: 1))
        NotificationCenter.default.addObserver(forName: .didSelectedDate, object: nil, queue: nil) { [weak self] (notification) in
            if let data = notification.object as? [String : AnyHashable]{
                let startDate = data["startDate"] as! Date
                let endDate = data["endDate"] as! Date
                print("\(startDate) \(endDate)")
                if let type = data["type"] as? JobScheduleType{
                    self?.didSelected(type: type, startDate: startDate, startTime: startDate, endDate: endDate, endTime: endDate)
                }
            }
        }
        configTable()
        print(self.postData.selectedSubCategories)
        if let cats = self.postData.selectedSubCategories{
            //            self.selectedSubCategoriesLbl.text = cats.map({$0.name!}).joined(separator: ", ")
            self.selectedSubCategoriesLbl.text = cats.joined(separator: ", ")
         }
    }
    
    func configTable(){
        tvDataSource = TableViewDataSource(items: items, tableView: tblSub, cellIdentifier: CellIdentifiers.JobInfoTypeCell.rawValue, configureCellBlock: { (cell, item, index) in
            guard let cell = cell as? JobInfoTypeCell else { return }
            let item = self.items[index!.row]
            Utility.shadow(mView: cell.base, viewRadius: 10, radius: 4, color: .lightGray)
            cell.item.text =  item.itemTitle
            cell.indexPath = index ?? IndexPath(item: 3, section: 0)
            if let data = item.selectedData{
                cell.desc.text = data
                cell.accessoryImg.image = #imageLiteral(resourceName: "tick-icon-1")
            }else{
                cell.desc.text = ""
                cell.accessoryImg.image = #imageLiteral(resourceName: "Arrow-sidemenu")
            }
            
            if item.itemIcon == #imageLiteral(resourceName: "Photo-icon-1") { //PHOTOS
                cell.collectionView.isHidden = false
//                cell.collectionView.backgroundColor = .gray
                cell.photos = self.selectedImages//item.photos ?? []
                cell.configureCollectionView()
            }else{
                cell.collectionView.isHidden = true
            }
            
            if item.itemIcon == #imageLiteral(resourceName: "privacy-icon-1"){ //ESTIMATTON PRICE
                cell.sliderBase.isHidden = false
                cell.configSlider()
                self.selectedMinPrice = cell.slider.selectedMinValue
                self.selectedMaxPrice = cell.slider.selectedMaxValue
                self.postData.startPrice = "\(Int(self.selectedMaxPrice))"
                self.postData.endPrice = "\(Int(self.selectedMaxPrice))"
                cell.sliderLbl.text = "\(Int(self.selectedMaxPrice))$" //  /"\(Int(self.selectedMinPrice))$ - \(Int(self.selectedMaxPrice))$"
            }else{
                cell.sliderBase.isHidden = true
            }
            cell.itemIcon.image = self.items[index!.row].itemIcon
 
        }, aRowSelectedListener: { (index, item) in
            switch index.row{
            case 0 :
                guard let vc = R.storyboard.newJob.addAddressVC() else { return }
                vc.delegate = self
                Router.shared.pushVC(vc: vc)
            case 1 :
                guard let vc = R.storyboard.newJob.addTimeVC() else { return }
//                vc.delegate = self
                Router.shared.pushVC(vc: vc)
            case 2:
                self.selectPicturesView.cvPhotos.registerXIB(CellIdentifiers.RectangularImageCell.rawValue)
                self.selectPicturesView.cvPhotos.delegate = self
                self.selectPicturesView.cvPhotos.dataSource = self
                self.view.addSubview(self.selectPicturesView)
                self.selectPicturesView.camBtn.addTarget(self, action: #selector(self.addBtn(sender:)), for: .touchUpInside)
                self.selectPicturesView.submitBtn.addTarget(self, action: #selector(self.photosAddedBtn(sender:)), for: .touchUpInside)

                 if self.items[2].photos?.count ?? 0 > 0{
                    self.selectPicturesView.camBtn.isHidden = true
                }else{
                    self.selectPicturesView.camBtn.isHidden = false
                }
            default:
                break
            }
        }, willDisplayCell: nil, viewforHeaderInSection: nil, scrollToTop: nil)
        tblSub.delegate = tvDataSource
        tblSub.dataSource = tvDataSource
        tblSub.reloadData()
    }
    
    @objc func addBtn(sender:UIButton){
        self.selectImagesFromPhone(collectionView: self.selectPicturesView.cvPhotos)
    }
    
    @objc func photosAddedBtn(sender:UIButton){
          let indexPath =  IndexPath(row: 2, section: 0) 
        
        self.items[2].photos = selectedImages
        self.postData.images = selectedImages
        self.selectPicturesView.removeFromSuperview()
        //        self.configAddPhotos(collectionView: self.selectPicturesView.cvPhotos)
        self.configTable()
//        self.tblSub.reloadRows(at: [indexPath], with: .automatic)
    }
      
    func selectImagesFromPhone(collectionView: UICollectionView){
        let alert = UIAlertController(title: "Browse" , message: "Choose image source", preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "Camera", style: .default, handler: { (_) in
            ImagePickerManager.shared.pickImage(self, source: .camera) { [weak self] (image, error) in
                if error != nil{
                    Toast.shared.showAlert(type: .apiFailure, message: error!)
                }else{
                    if let img = image {
                        self?.selectedImages.append(img)
                        self?.selectPicturesView.cvPhotos.reloadData()
                        self?.tblSub.reloadData()
                        //                        self?.configAddPhotos(collectionView: collectionView)
                    }
                }
            }
        }))
        
        alert.addAction(UIAlertAction(title: "Photos", style: .default, handler: { (_) in
            ImagePickerManager.shared.pickImage(self, source: .photoLibrary) { [weak self] (image, error) in
                if error != nil{
                    Toast.shared.showAlert(type: .apiFailure, message: error!)
                }else{
                    if let img = image {
                        self?.selectedImages.append(img)
                        self?.selectPicturesView.cvPhotos.reloadData()
                        self?.tblSub.reloadData()
                        
                        //                        self?.configAddPhotos(collectionView: collectionView)
                        
                        //                        collectionView.reloadData()
                    }
                }
            }
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: { (_) in
            print("You've pressed the Cancel")
        }))
        self.present(alert, animated: true, completion: nil)
    }
     //MARK: - IBAction Methods

    @IBAction func changeCategoryBtn(_ sender: Any) {
        Router.shared.popFromInitialNav()

    }
    @IBAction func btnActionBack(_ sender: Any) {
        Router.shared.popFromInitialNav()
    }
    
    @IBAction func btnActionSubmit(_ sender: Any){
        guard let cell = self.tblSub.cellForRow(at: IndexPath(row: 3, section: 0)) as? JobInfoTypeCell
            else{
                return
        }
        let slider = cell.slider
        setEstimatedPrice(min: slider!.selectedMaxValue, max: slider!.selectedMaxValue)

        if postData.address == nil{
            Toast.shared.showAlert(type: .validationFailure, message: "Address is missing")
        }else if postData.startDate == nil || postData.endDate == nil{
            Toast.shared.showAlert(type: .validationFailure, message: "Please set up the job schedule")
        }else {
            let vc = R.storyboard.newJob.addDescriptionVC()!
            vc.postData = self.postData
            Router.shared.pushVC(vc: vc)
        }
    }
    
    func setEstimatedPrice(min: CGFloat, max:CGFloat){
        let minPrice = Int(min)
        let maxPrice = Int(max)
        selectedMinPrice = min
        selectedMaxPrice = max
        
        //        self.sliderLbl.text = "\(minPrice)$ - \(maxPrice)$"
        self.postData.startPrice = "\(minPrice)"
        self.postData.endPrice = "\(maxPrice)"
        //        self.selectedMinPrice = min
        for (index, element) in items.enumerated() {
            if element.itemIcon == #imageLiteral(resourceName: "privacy-icon-1"){
                //                self.tblSub.reloadRows(at: [IndexPath(item: index, section: 0)], with: .none)
                break
            }
        }
        //        self.tblSub.reloadData()
    }
    
}

//extension AddInfoTypeVC: RangeSeekSliderDelegate{
//
//    func rangeSeekSlider(_ slider: RangeSeekSlider, didChange minValue: CGFloat, maxValue: CGFloat) {
//        setEstimatedPrice(min: slider.selectedMinValue, max: slider.selectedMaxValue)
//    }
//
//    func didEndTouches(in slider: RangeSeekSlider) {
//        //        setEstimatedPrice(min: slider.selectedMinValue, max: slider.selectedMaxValue)
//    }
//
//}


extension AddInfoTypeVC: AddAddressVCDelegate{
    
    func didSaved(address: String,apartmentNo: String, city: String, state: String, country: String, pincode: String, coordinates: CLLocationCoordinate2D) {
        self.postData.address = address
        self.postData.apartmentNo = apartmentNo
        self.postData.city = city
        self.postData.state = state
        self.postData.zipCode = pincode
        self.postData.lat = coordinates.latitude
        self.postData.long  = coordinates.longitude
        
        for (index, element) in items.enumerated() {
            if element.itemIcon == #imageLiteral(resourceName: "where-icon"){
                items[index].selectedData = self.postData.address
                self.tblSub.reloadRows(at: [IndexPath(row: index, section: 0)], with: .automatic)
                break
            }
        }
        print(postData)
    }
    
}

extension AddInfoTypeVC {//: AddTimeVCDelegate{
    
    func didSelected(type: JobScheduleType, startDate: Date, startTime: Date, endDate:Date, endTime: Date) {
        postData.type = type.rawValue
//        postData.date = "\(date.timeIntervalSince1970)"
//        postData.time = "\(time.timeIntervalSince1970)"
        postData.startDate = "\(startDate.timeIntervalSince1970)"
        postData.startTime = "\(startTime.timeIntervalSince1970)"
        postData.endDate = "\(endDate.timeIntervalSince1970)"
        postData.endTime = "\(endTime.timeIntervalSince1970)"
        
        for (index, element) in items.enumerated() {
            if element.itemIcon == #imageLiteral(resourceName: "calendar-icon-1") {
                items[index].selectedData = dateFmtr.string(from: startDate)
                self.tblSub.reloadRows(at: [IndexPath(row: index, section: 0)], with: .automatic)
                break
            }
        }
    }
    
//    @objc func didSelectedDate(sender: NSNotification){
//        if let data = sender.object as? [String : AnyHashable]{
//            let startDate = data["startDate"] as! Date
//            let endDate = data["endDate"] as! Date
//            print(startDate)
//        }
//    }
    
}

extension AddInfoTypeVC : UICollectionViewDelegate,UICollectionViewDataSource, UICollectionViewDelegateFlowLayout{
    
//    func configAddPhotos(collectionView: UICollectionView){
//        //        guard let photos = selectedImages else { return }
//        let dataSource = CollectionDataSource(_items: selectedImages,_identifier: CellIdentifiers.RectangularImageCell.rawValue, _collectionView: collectionView, _size: CGSize(width: ScreenSize.SCREEN_WIDTH / 2, height: ScreenSize.SCREEN_WIDTH / 2), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil, configureCell: { (cell, item, indexPath) in
//            guard let item = item as? UIImage else { return }
//
//            guard let cell = cell as? RectangularImageCell else { return }
//            cell.img.image = item
//
//
//        }, didSelectedItem: { (indexPath, item) in
//            guard let item = item else { return }
//
//        });
//        collectionView.dataSource = dataSource
//        collectionView.delegate = dataSource
//        collectionView.reloadData()
//        //init(_items: photos, _identifier: CellIdentifiers.RectangularImageCell.rawValue, _collectionView: collectionView, _size: CGSize(width: ScreenSize.SCREEN_WIDTH / 2.5, height: 44), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil)
//    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let wd =  collectionView.frame.size.width / 2.5
        return CGSize(width:wd, height: wd)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // let item =  selectedImages//items[2]
        let count = selectedImages.count//item.photos == nil ? 0 : item.photos!.count
        return count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CellIdentifiers.RectangularImageCell.rawValue, for: indexPath) as! RectangularImageCell
 
        let item = selectedImages[indexPath.row]
        cell.img.image = item
 
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
//        if indexPath.row == 0{
//            self.selectImagesFromPhone(collectionView: collectionView)
//        }
        
        
    }
    
    
}
