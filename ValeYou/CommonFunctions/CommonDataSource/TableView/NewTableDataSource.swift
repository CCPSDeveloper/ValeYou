//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import UIKit

typealias  ListCellConfigureBlock = (_ cell : Any , _ item : Any? , _ indexpath: IndexPath?) -> ()
typealias  DidSelectedRow = (_ indexPath : IndexPath , _ item : Any) -> ()
typealias  ViewForHeaderInSection = (_ section : Int) -> UIView?
typealias  WillDisplayCell = (_ indexPath : IndexPath, _ cell : Any , _ item : Any?) -> ()
typealias DidEndDisplayingCell = (_ indexPath:IndexPath, _ cell:Any, _ item:Any?) -> ()
typealias ScrollToTop = ()->()
typealias ScrollToBottom = ()->()


class TableViewDataSource: NSObject  {
    
    var items : Array<Any>?
    var cellIdentifier : String?
    var tableView  : UITableView?
    
    var configureCellBlock : ListCellConfigureBlock?
    var aRowSelectedListener : DidSelectedRow?
    var viewforHeaderInSection : ViewForHeaderInSection?
    var scrollToTop:ScrollToTop?
    var scrollToBottom:ScrollToBottom?
    var willDisplayCell : WillDisplayCell?
    var headerHeight : CGFloat?
    var footerHeight : CGFloat?
    var cellHeight: CGFloat = UITableView.automaticDimension
    var emptyListMessage: String?
    
    init (items : Array<Any>? , tableView : UITableView? , cellIdentifier : String? , configureCellBlock : ListCellConfigureBlock? , aRowSelectedListener :   DidSelectedRow?,willDisplayCell : WillDisplayCell? , viewforHeaderInSection : ViewForHeaderInSection?,scrollToTop:ScrollToTop?,scrollToBottom:ScrollToBottom? = nil, emptyListMessage: String? = nil) {
        
        self.tableView = tableView
        self.items = items
        self.cellIdentifier = cellIdentifier
        self.configureCellBlock = configureCellBlock
        self.aRowSelectedListener = aRowSelectedListener
        self.willDisplayCell = willDisplayCell
        self.viewforHeaderInSection = viewforHeaderInSection
        self.scrollToTop = scrollToTop
        self.scrollToBottom = scrollToBottom
        
        
    }
    
    override init() {
        super.init()
    }
}

extension TableViewDataSource : UITableViewDelegate , UITableViewDataSource, UIScrollViewDelegate{
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        guard let identifier = cellIdentifier else {
            fatalError("Cell identifier not provided")
        }
        
        let cell: UITableViewCell = tableView.dequeueReusableCell(withIdentifier: identifier , for: indexPath) as UITableViewCell
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if let block = self.configureCellBlock , let item: Any = self.items?[indexPath.row]{
            block(cell , item , indexPath as IndexPath?)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if let block = self.aRowSelectedListener, case let item = self.items?[indexPath.row]{
            block(indexPath , item)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.items?.count ?? 0 < 0{
            self.tableView?.setEmptyMessage(emptyListMessage ?? "No Data")
        }else{
            self.tableView?.restore()
        }
        
        return self.items?.count ?? 0
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return 300
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return cellHeight
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let block = viewforHeaderInSection else { return nil }
        return block(section)
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        guard let block = willDisplayCell, indexPath.row < /items?.count else { return }
        return block(indexPath, cell, items?[indexPath.row])
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return headerHeight ?? 0.0
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        guard let block = viewforHeaderInSection else { return nil }
        return block(section)
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return footerHeight ?? 0.0
    }
    
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == tableView{
            if scrollView.contentOffset.y == 0{
                scrollToTop?()
            }else{
                
                if scrollView.contentOffset.y + 100 >= (scrollView.contentSize.height - scrollView.frame.size.height){
                    if let block = scrollToBottom{
                        block()
                    }
                }
            }
        }
    }
    
    func getLabelHeight(text: String, width: CGFloat, font: UIFont) -> CGFloat {
        let lbl = UILabel(frame: .zero)
        lbl.frame.size.width = width
        lbl.font = font
        lbl.numberOfLines = 0
        lbl.text = text
        lbl.sizeToFit()

        return lbl.frame.size.height
    }
}



