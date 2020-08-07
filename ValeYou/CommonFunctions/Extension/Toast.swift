//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import SwiftEntryKit

class Toast {
    
    static let shared = Toast()
    
    func showAlert(type: AlertType, message: String) {
        var attributes = EKAttributes()
        attributes.windowLevel = .statusBar
        attributes.position = .bottom
        attributes.displayDuration = 1.5
        attributes.entryBackground = .color(color: EKColor(#colorLiteral(red: 0.1968357265, green: 0.252646327, blue: 0.2385596037, alpha: 1)))
        attributes.positionConstraints.safeArea = .empty(fillSafeArea: true)
        let title = EKProperty.LabelContent.init(text: type.title.localize, style: .init(font: UIFont.systemFont(ofSize: 18, weight: .bold), color: EKColor(.white)))
        let description = EKProperty.LabelContent.init(text: message.localize, style: .init(font: UIFont.systemFont(ofSize: 16, weight: .medium), color: EKColor(.white)))
        let simpleMessage = EKSimpleMessage.init(title: title, description: description)
        let notificationMessage = EKNotificationMessage.init(simpleMessage: simpleMessage)
        let contentView = EKNotificationMessageView(with: notificationMessage)
        SwiftEntryKit.display(entry: contentView, using: attributes)
    }
    
}
