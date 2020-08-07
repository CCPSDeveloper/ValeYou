////  MEX
////
////  Created by Pankaj Sharma on 03/04/20.
////  Copyright © 2020 Pankaj Sharma. All rights reserved.
////
//
//import Foundation
//import UIKit
//
//class AddImagesCollectionDataSource: CollectionDataSource {
//    
//    override init(_items: Array<Any>?, _identifier: String?, _collectionView: UICollectionView,  _size: CGSize?,  _edgeInsets: UIEdgeInsets?,  _lineSpacing: CGFloat?,  _interItemSpacing: CGFloat?) {
//        super.init()
//        items = _items
//        identifier = _identifier
//        collectionView = _collectionView
//        size = _size
//        edgeInsets = _edgeInsets
//        minLineSpacing = _lineSpacing
//        minInterItemSpacing = _interItemSpacing
//        collectionView?.dataSource = self
//        collectionView?.delegate = self
//        collectionView?.reloadData()
//        
//    }
//    
//    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> //UICollectionViewCell {
////        switch indexPath.item{
//        case 0:
////            identifier = CellIdentifiers.AddImageCell.rawValue
////        default:
////            identifier = CellIdentifiers.ImageCell.rawValue
////        }
//        
//        let mCell:UICollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: identifier!, for: indexPath)
//        print("items",items?.count)
//        print("index",indexPath.item)
//        if indexPath.item == 0{
//            if let block = self.configureCell {
//                       block(mCell,nil,indexPath)
//                   }
//        }else{
//            if let block = self.configureCell ,let item = self.items?[indexPath.item - 1]{
//                       block(mCell,item,indexPath)
//                   }
//        }
//       
//        return mCell
//    }
//    
//    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
//        return (items?.count ?? 0) + 1
//    }
//}
//
