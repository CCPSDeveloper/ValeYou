//
//  JobInfoTypeCell.swift
//  ValeYou
//
//  Created by Techwin on 18/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import RangeSeekSlider

class JobInfoTypeCell: UITableViewCell {
    
    @IBOutlet weak var accessoryImg: UIImageView!
    @IBOutlet weak var itemIcon: UIImageView!
    @IBOutlet weak var item: UILabel!
    @IBOutlet weak var desc: UILabel!
    @IBOutlet weak var collectionView: UICollectionView!{
        didSet{
            collectionView.registerXIB(CellIdentifiers.RectangularImageCell.rawValue)
        }
    }
    @IBOutlet weak var base: UIView!
    @IBOutlet weak var sliderLbl: UILabel!
    @IBOutlet weak var slider: RangeSeekSlider!
    @IBOutlet weak var sliderBase: UIView!{
        didSet{
            slider.delegate = self
        }
    }
    var indexPath : IndexPath?
    var selectedMinPrice = CGFloat()
    var selectedMaxPrice = CGFloat()
    var photos = [UIImage]()
    
    override func awakeFromNib(){
        super.awakeFromNib()
 
    }
    
    override func setSelected(_ selected: Bool, animated: Bool){
        super.setSelected(selected, animated: animated)
    }
 
    func configSlider(){
        slider.delegate = self
    }
    
}

extension JobInfoTypeCell: RangeSeekSliderDelegate{
    
    func rangeSeekSlider(_ slider: RangeSeekSlider, didChange minValue: CGFloat, maxValue: CGFloat) {
        self.sliderLbl.text = "\(Int(maxValue))$"
        selectedMinPrice = maxValue//minValue
        selectedMaxPrice = maxValue
     }
    
}


extension JobInfoTypeCell {
    
    func configureCollectionView(){
//        guard let photos = photos else { return }
        print("photos: ",photos)
        let dataSource = CollectionDataSource(_items: photos, _identifier: CellIdentifiers.RectangularImageCell.rawValue, _collectionView: collectionView, _size: CGSize(width: ScreenSize.SCREEN_WIDTH / 2.5, height: 44), _edgeInsets: nil, _lineSpacing: nil, _interItemSpacing: nil, configureCell: { (cell, item, indexPath) in
            print(item as Any)
            guard let item = item as? UIImage else { return }
            guard let cell = cell as? RectangularImageCell else { return }
            cell.img.image = item
            cell.base.cornerRadius = 10
            
        }, didSelectedItem: { (indexPath, item) in
            guard let item = item else { return }
            
        })
            collectionView.dataSource = dataSource
            collectionView.delegate = dataSource
            collectionView.reloadData()
         
    }
    
}
