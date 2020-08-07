//
//  MyJobsVC + API.swift
//  ValeYou
//
//  Created by Techwin on 21/07/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation

extension MyJobsVC{
    
    func getJobs(pageNo:Int, type: UserJobType ){
        
        UserEP.userJobList(page: 1, limit: 20, type: type.rawValue).request(loader: true, success: { (data) in
            guard let data = data as? UserJobListModel else { return }
            self.jobs = data.data
            print("jobs count: \(self.jobs.count)")
            self.configureCurrentTbl()
            
            self.tblCurrent.reloadData()
            self.tblPast.reloadData()
            let typeName = type == .all ? "\(getJobTypeName(with: type).title) Jobs" : getJobTypeName(with: self.selectedType).title
            self.btnCurrent.setTitle(typeName, for: .normal)
            
        }) { (error) in
            if let error = error{
                Toast.shared.showAlert(type: .apiFailure, message: error)
            }
        }
    }
}
