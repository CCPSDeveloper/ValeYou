//
//  CustomUITextView.swift
//  Rugrats
//
//  Created by Techwin on 22/07/20.
//  Copyright © 2020 Techwin. All rights reserved.
//

import UIKit
//
//class CustomUITextView: UITextView {
//
//    /*
//    // Only override draw() if you perform custom drawing.
//    // An empty implementation adversely affects performance during animation.
//    override func draw(_ rect: CGRect) {
//        // Drawing code
//    }
//    */
//
//}

//@IBDesignable class UIPlaceholderTextView: UITextView {
//
//    var placeholderLabel: UILabel?
//
//    override init(frame: CGRect, textContainer: NSTextContainer?) {
//        super.init(frame: frame, textContainer: textContainer)
//        sharedInit()
//    }
//
//    required init?(coder aDecoder: NSCoder) {
//        super.init(coder: aDecoder)
//        sharedInit()
//    }
//
//    override func prepareForInterfaceBuilder() {
//        sharedInit()
//    }
//
//    func sharedInit() {
//        refreshPlaceholder()
//        NotificationCenter.default.addObserver(self, selector: #selector(textChanged), name: UITextView.textDidChangeNotification, object: nil)
//    }
//
//    @IBInspectable var placeholder: String? {
//        didSet {
//            refreshPlaceholder()
//        }
//    }
//
//    @IBInspectable var placeholderColor: UIColor? = .darkGray {
//        didSet {
//            refreshPlaceholder()
//        }
//    }
//
//    @IBInspectable var placeholderFontSize: CGFloat = 14 {
//        didSet {
//            refreshPlaceholder()
//        }
//    }
//
//    func refreshPlaceholder() {
//        if placeholderLabel == nil {
//            placeholderLabel = UILabel()
//            let contentView = self.subviews.first ?? self
//
//            contentView.addSubview(placeholderLabel!)
//            placeholderLabel?.translatesAutoresizingMaskIntoConstraints = false
//
//            placeholderLabel?.leftAnchor.constraint(equalTo: contentView.leftAnchor, constant: textContainerInset.left + 4).isActive = true
//            placeholderLabel?.rightAnchor.constraint(equalTo: contentView.rightAnchor, constant: textContainerInset.right + 4).isActive = true
//            placeholderLabel?.topAnchor.constraint(equalTo: contentView.topAnchor, constant: textContainerInset.top).isActive = true
//            placeholderLabel?.bottomAnchor.constraint(lessThanOrEqualTo: contentView.bottomAnchor, constant: textContainerInset.bottom).isActive = true
//        }
//        placeholderLabel?.text = placeholder
//        placeholderLabel?.textColor = placeholderColor
//        placeholderLabel?.font = UIFont.systemFont(ofSize: placeholderFontSize)
//    }
//
//    @objc func textChanged() {
//        if self.placeholder?.isEmpty ?? true {
//            return
//        }
//
//        UIView.animate(withDuration: 0.25) {
//            if self.text.isEmpty {
//                self.placeholderLabel?.alpha = 1.0
//            } else {
//                self.placeholderLabel?.alpha = 0.0
//            }
//        }
//    }
//
//    override var text: String! {
//        didSet {
//            textChanged()
//        }
//    }
//
//}


protocol UIPlaceholderTextViewDelegate {
    func placeholderTextViewDidChangeText(_ text:String)
    func placeholderTextViewDidEndEditing(_ text:String)
}

final class UIPlaceholderTextView: UITextView {

    var notifier:UIPlaceholderTextViewDelegate?

    var placeholder: String? {
        didSet {
            placeholderLabel?.text = placeholder
        }
    }
    var placeholderColor = UIColor.placeholderText
    var placeholderFont = R.font.montserratBold(size: 17) {
        didSet {
            placeholderLabel?.font = placeholderFont
        }
    }

    fileprivate var placeholderLabel: UILabel?

    // MARK: - LifeCycle

    init() {
        super.init(frame: CGRect.zero, textContainer: nil)
        awakeFromNib()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    override func awakeFromNib() {
        super.awakeFromNib()

        self.delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(UIPlaceholderTextView.textDidChangeHandler(notification:)), name: UITextView.textDidChangeNotification, object: nil)

        placeholderLabel = UILabel()
        placeholderLabel?.textColor = placeholderColor
        placeholderLabel?.text = placeholder
        placeholderLabel?.textAlignment = .left
        placeholderLabel?.numberOfLines = 0
    }

    override func layoutSubviews() {
        super.layoutSubviews()

        placeholderLabel?.font = placeholderFont

        var height:CGFloat = placeholderFont?.lineHeight ?? 0
        if let data = placeholderLabel?.text {

            let expectedDefaultWidth:CGFloat = bounds.size.width
            let fontSize:CGFloat = placeholderFont?.pointSize ?? 0

            let textView = UITextView()
            textView.text = data
            textView.font = R.font.montserratBold(size: 17)//UIFont.appMainFontForSize(fontSize)
            let sizeForTextView = textView.sizeThatFits(CGSize(width: expectedDefaultWidth,
                                                               height: CGFloat.greatestFiniteMagnitude))
            let expectedTextViewHeight = sizeForTextView.height

            if expectedTextViewHeight > height {
                height = expectedTextViewHeight
            }
        }

        placeholderLabel?.frame = CGRect(x: 5, y: 0, width: bounds.size.width - 16, height: height)

        if text.isEmpty {
            addSubview(placeholderLabel!)
            bringSubviewToFront(placeholderLabel!)
        } else {
            placeholderLabel?.removeFromSuperview()
        }
    }

    @objc func textDidChangeHandler(notification: Notification) {
        layoutSubviews()
    }

}

extension UIPlaceholderTextView : UITextViewDelegate {
    // MARK: - UITextViewDelegate
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        if(text == "\n") {
            textView.resignFirstResponder()
            return false
        }
        return true
    }

    func textViewDidChange(_ textView: UITextView) {
        notifier?.placeholderTextViewDidChangeText(textView.text)
    }

    func textViewDidEndEditing(_ textView: UITextView) {
        notifier?.placeholderTextViewDidEndEditing(textView.text)
    }
}
