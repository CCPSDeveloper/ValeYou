//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import MobileCoreServices

extension Dictionary {
    func allKeys() -> [String] {
        guard self.keys.first is String else {
            debugPrint("This function will not return other hashable types. (Only strings)")
            return []
        }
        return self.compactMap { (anEntry) -> String? in
            guard let temp = anEntry.key as? String else { return nil }
            return temp }
    }
}


extension String {
    
     var localize: String {
            return NSLocalizedString(self, comment:"")
        }
    
    func mimeTypeFromFileExtension() -> String? {
        guard let uti: CFString = UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, self as NSString, nil)?.takeRetainedValue() else {
            return nil
        }
        
        guard let mimeType: CFString = UTTypeCopyPreferredTagWithClass(uti, kUTTagClassMIMEType)?.takeRetainedValue() else {
            return nil
        }
        
        return mimeType as String
    }
    
    func widthOfString(usingFont font: UIFont) -> CGFloat {
        let fontAttributes = [NSAttributedString.Key.font: font]
        let size = self.size(withAttributes: fontAttributes)
        return size.width
    }
    
    func heightOfString(usingFont font: UIFont) -> CGFloat {
        let fontAttributes = [NSAttributedString.Key.font: font]
        let size = self.size(withAttributes: fontAttributes)
        return size.height
    }
}

extension String {
    var isPhoneNumber: Bool {
        do {
            let detector = try NSDataDetector(types: NSTextCheckingResult.CheckingType.phoneNumber.rawValue)
            let matches = detector.matches(in: self, options: [], range: NSMakeRange(0, self.count))
            if let res = matches.first {
                return res.resultType == .phoneNumber && res.range.location == 0 && res.range.length == self.count
            } else {
                return false
            }
        } catch {
            return false
        }
    }
}


extension String {
    func height(withConstrainedWidth width: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: width, height: .greatestFiniteMagnitude)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: font], context: nil)
        return ceil(boundingBox.height)
    }
    
    func width(withConstraintedHeight height: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: .greatestFiniteMagnitude, height: height)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: font], context: nil)
        
        return ceil(boundingBox.width)
    }
}


extension NSMutableAttributedString {
    
    @discardableResult func bold(_ text: String) -> NSMutableAttributedString {
        
        var attributes = [NSAttributedString.Key: Any]()
        attributes[NSAttributedString.Key.foregroundColor] = UIColor.black
        //        let paragraph = NSMutableParagraphStyle()
        //        paragraph.alignment = .center
        //        attributes[.paragraphStyle] = paragraph
        //        attributes[NSAttributedStringKey.font] =  R.font.camptonSemiBold(size: 12)
        let boldString = NSMutableAttributedString(string:text, attributes: attributes)
        append(boldString)
        return self
    }
    
    @discardableResult func italic(_ text: String) -> NSMutableAttributedString {
        var attributes = [NSAttributedString.Key: Any]()
        attributes[NSAttributedString.Key.foregroundColor] = UIColor.black
        //        attributes[NSAttributedStringKey.font] =  UIFont(name: "OpenSans-ExtraBoldItalic", size: 14)!
        let boldString = NSMutableAttributedString(string:text, attributes: attributes)
        append(boldString)
        
        return self
    }
    
    @discardableResult func boldB(_ text: String) -> NSMutableAttributedString {
        
        var attributes = [NSAttributedString.Key: Any]()
        attributes[NSAttributedString.Key.foregroundColor] = UIColor.black.withAlphaComponent(0.75)
        //        attributes[NSAttributedString.Key.font] =  UIFont(name: "Campton-SemiBold", size: 14)!
        let boldString = NSMutableAttributedString(string:text, attributes: attributes)
        append(boldString)
        return self
    }
    
    @discardableResult func regular(_ text: String) -> NSMutableAttributedString {
        var attributes = [NSAttributedString.Key: Any]()
        attributes[NSAttributedString.Key.foregroundColor] = UIColor.black.withAlphaComponent(0.55)
        //        attributes[NSAttributedStringKey.font] =  R.font.camptonMedium(size: 12)
        let boldString = NSMutableAttributedString(string:text, attributes: attributes)
        append(boldString)
        
        return self
    }
}



extension String {
    
    func convertTo( format: String) -> String{
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format
        
        let startDate = dateFormatter.date(from: self) ?? Date()
        let dateStr = dateFormatter.string(from: startDate)
        return dateStr 
    }
    
    func fromTimeStampToDate(with format: String? = nil)  -> String{
        let timeStamp = TimeInterval(Double(self)!)
        let date = Date(timeIntervalSince1970: timeStamp )
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format ?? kDefaultDateFormat
        
//        let startDate = dateFormatter.date(from: date) ?? Date()
        let dateStr = dateFormatter.string(from: date)
        return dateStr
        
    }
    
    func toBoolVal() -> Bool {
        return self == "1" || self == "true" || self == "yes"
    }
    
}


func toJson(data: OptionalDictionary) -> String {
    do {
        let jsonData = try JSONSerialization.data(withJSONObject: data, options: JSONSerialization.WritingOptions.prettyPrinted)
        var string = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue) ?? ""
        string = string.replacingOccurrences(of: "\n", with: "") as NSString
        print(string)
        string = string.replacingOccurrences(of: "\\", with: "") as NSString
        print(string)
        //            string = string.replacingOccurrences(of: "\"", with: "") as NSString
        string = string.replacingOccurrences(of: " ", with: "") as NSString
        print(string)
        return string as String
    }
    catch let error as NSError{
        print(error.description)
        return ""
    }
}


extension Array where Element : Any{

    func toJson() -> String {
        do {
            let data = self
            let jsonData = try JSONSerialization.data(withJSONObject: data, options: JSONSerialization.WritingOptions.prettyPrinted)
            var string = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue) ?? ""
            string = string.replacingOccurrences(of: "\n", with: "") as NSString
            print(string)
            string = string.replacingOccurrences(of: "\\", with: "") as NSString
            print(string)
            //            string = string.replacingOccurrences(of: "\"", with: "") as NSString
            string = string.replacingOccurrences(of: " ", with: "") as NSString
            print(string)
            return string as String
        }
        catch let error as NSError{
            print(error.description)
            return ""
        }
    }
    
    
    
    func indexOfObject(object : Any) -> NSInteger {
        return (self as NSArray).index(of: object)
    }
    
}    



extension String {
    var firstUppercased: String {
        guard let first = first else { return "" }
        return String(first).uppercased() + dropFirst()
    }
    
    
}

extension Double {
    /// Rounds the double to decimal places value
    func rounded(toPlaces places:Int) -> Double {
        let divisor = pow(10.0, Double(places))
        return (self * divisor).rounded() / divisor
    }
}


extension String {
    
    public var length: Int {
        return self.count
    }
    
    var isValidEmail : Bool {
        if length <= 0 { return false }
        if isBlank { return false }
        if isEmail { return true }
        return false
    }
    
    public var isEmail: Bool {
        let dataDetector = try? NSDataDetector(types: NSTextCheckingResult.CheckingType.link.rawValue)
        let firstMatch = dataDetector?.firstMatch(in: self, options: NSRegularExpression.MatchingOptions.reportCompletion, range: NSRange(location: 0, length: length))
        return (firstMatch?.range.location != NSNotFound && firstMatch?.url?.scheme == "mailto")
    }
    
    
    public var isBlank: Bool {
        get {
            let trimmed = trimmingCharacters(in: .whitespacesAndNewlines)
            return trimmed.isEmpty
        }
    }
    
    public var removeSpace: String {
        let trimmedString = self.trimmingCharacters(in: .whitespacesAndNewlines)
        return trimmedString
    }
    
    
}
extension String {
    func addBoldText(fullString: NSString, boldPartsOfString: Array<NSString>, fontSize: CGFloat!, color: UIColor) -> NSAttributedString {
        let nonBoldFontAttribute: [NSAttributedString.Key: Any] = [NSAttributedString.Key.font: UIFont(name: "SFUIDisplay-Medium", size: 15)!]
        let boldFontAttribute: [NSAttributedString.Key: Any] = [NSAttributedString.Key.font: UIFont(name: "SFUIDisplay-Bold", size: fontSize)!, NSAttributedString.Key.foregroundColor: color]
        let boldString = NSMutableAttributedString(string: fullString as String, attributes:nonBoldFontAttribute)
        for i in 0 ..< boldPartsOfString.count {
            boldString.addAttributes(boldFontAttribute, range: fullString.range(of: boldPartsOfString[i] as String))
        }
        return boldString
    }
    
    func attributedString(from string: String, nonBoldRange: NSRange?) -> NSAttributedString {
        let attrs = [
            NSAttributedString.Key.font: UIFont(name: "SFUIDisplay-Medium", size: 15),
            NSAttributedString.Key.foregroundColor: UIColor.lightGray
        ]
        let nonBoldAttribute = [
            NSAttributedString.Key.font: UIFont(name: "SFUIDisplay-Bold", size: 15),
            NSAttributedString.Key.foregroundColor: UIColor.black
        ]
        let attrStr = NSMutableAttributedString(string: string, attributes: attrs as [NSAttributedString.Key : Any])
        if let range = nonBoldRange {
            attrStr.setAttributes(nonBoldAttribute as [NSAttributedString.Key : Any], range: range)
        }
        return attrStr
    }
}
