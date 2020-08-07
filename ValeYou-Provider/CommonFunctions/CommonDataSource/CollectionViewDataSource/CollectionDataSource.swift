//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit

class CollectionDataSource: NSObject {
    
    typealias  DidSelectedItem = (_ indexPath : IndexPath, _ item: Any?) -> Void
    typealias  ListCollectionConfig = (_ cell : UICollectionViewCell , _ item : Any?, _ indexpath: IndexPath) -> ()
    
    typealias WillDisplay = (_ indexPath: IndexPath) -> ()
    
    typealias DidScroll = ()->()
    
    var items: Array<Any>?
    var identifier: String?
     var collectionView: UICollectionView?
    var size: CGSize?
     var edgeInsets: UIEdgeInsets?
     var minLineSpacing: CGFloat?
     var minInterItemSpacing: CGFloat?
    
    var didSelectItem: DidSelectedItem?
    var configureCell: ListCollectionConfig?
    var willDisplay: WillDisplay?
    var didScroll:DidScroll?
    
    init(_items: Array<Any>?, _identifier: String?, _collectionView: UICollectionView,  _size: CGSize?,  _edgeInsets: UIEdgeInsets?,  _lineSpacing: CGFloat?,  _interItemSpacing: CGFloat?) {
        super.init()
        items = _items
        identifier = _identifier
        collectionView = _collectionView
        size = _size
        edgeInsets = _edgeInsets
        minLineSpacing = _lineSpacing
        minInterItemSpacing = _interItemSpacing
        collectionView?.dataSource = self
        collectionView?.delegate = self
        collectionView?.reloadData()
        
    }
    override init() {
        super.init()
    }
}

extension CollectionDataSource: UICollectionViewDataSource, UICollectionViewDelegate  {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
//        if items?.count == 0 {
//            let objNoDataFound =  UINib(nibName: "NoDataView", bundle: nil).instantiate(withOwner: nil, options: nil)[0] as! UIView
//            self.collectionView?.backgroundView = objNoDataFound
//        }
        
        return items?.count ?? 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: identifier ?? "", for: indexPath)
        configureCell?(cell, items?[indexPath.item], indexPath)
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        didSelectItem?(indexPath, items?[indexPath.item])
    }
     func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        if let block = willDisplay {
            block(indexPath)
        }
    }
    
//    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAtIndex section: Int) -> UIEdgeInsets {
//
////        let totalCellWidth =  * CellCount
////        let totalSpacingWidth = CellSpacing * (CellCount - 1)
////
////        let leftInset = (collectionViewWidth - CGFloat(totalCellWidth + totalSpacingWidth)) / 2
////        let rightInset = leftInset
//
//        return UIEdgeInsets(top: 0, left: leftInset, bottom: 0, right: rightInset)
//    }
}
extension CollectionDataSource: UICollectionViewDelegateFlowLayout,UIScrollViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
      
        return size ?? CGSize.zero
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if let block = didScroll {
            block()
        }
    }
    
}
