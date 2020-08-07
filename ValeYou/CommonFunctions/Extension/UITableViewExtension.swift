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
    
    func setEmptyMessage(_ message: String) {
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height))
        messageLabel.text = message
        messageLabel.textColor = UIColor.black
        //UIColor(red: 238/255, green: 76/255, blue: 93/255, alpha: 1.0)
        messageLabel.numberOfLines = 0;
        messageLabel.textAlignment = .center;
        messageLabel.center  = self.center
        messageLabel.font =  R.font.montserratMedium(size: 17)
        messageLabel.sizeToFit()
        self.backgroundView = messageLabel;
    }
    
    func restore() {
        self.backgroundView = nil
    }
    
    func getIndexPathFor(button: UIButton) -> IndexPath?{
        let point = self.convert(button.bounds.origin, from: button)
        let indexPath = self.indexPathForRow(at: point)
        //        //        let sectionIndex = tableView
        return indexPath
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

