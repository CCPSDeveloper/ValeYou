//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import UIKit

extension UITableView {
    
    func registerCell(identifier: String) {
          let xNib = UINib(nibName: identifier, bundle: nil)
          self.register(xNib, forCellReuseIdentifier: identifier)
      }
    
    
    func sizeHeaderToFit() {
        let headerView = self.tableHeaderView
        headerView?.setNeedsLayout()
        headerView?.layoutIfNeeded()
        let height = headerView?.systemLayoutSizeFitting(UIView.layoutFittingCompressedSize).height
        var frame = headerView?.frame
        frame?.size.height = height ?? 0.0
        headerView?.frame = frame ?? CGRect.init()
        self.tableHeaderView = headerView
    }
    
    func registerXIB(_ nibName: String) {
        self.register(UINib.init(nibName: nibName, bundle: nil), forCellReuseIdentifier: nibName)
    }
    
    func registerXIBForHeaderFooter(_ nibName: String) {
        self.register(UINib.init(nibName: nibName, bundle: nil), forHeaderFooterViewReuseIdentifier: nibName)
    }
}

extension UICollectionView {
    func registerXIB(_ nibName: String) {
        self.register(UINib.init(nibName: nibName, bundle: nil), forCellWithReuseIdentifier: nibName)
    }
}


class SelfSizedTableView: UITableView {
  var maxHeight: CGFloat = UIScreen.main.bounds.size.height
  
  override func reloadData() {
    super.reloadData()
    self.invalidateIntrinsicContentSize()
    self.layoutIfNeeded()
  }
  
  override var intrinsicContentSize: CGSize {
    let height = min(contentSize.height, maxHeight)
    return CGSize(width: contentSize.width, height: height)
  }
}

