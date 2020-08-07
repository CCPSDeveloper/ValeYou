//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//
import UIKit

extension Date{
    
    func getDateFromStr(str: String , newFormat: String) -> Date{
        let formatter = DateFormatter()
        formatter.dateStyle = .full
        formatter.timeZone = NSTimeZone.local
        formatter.dateFormat = newFormat
        let date = formatter.date(from: str)
        return date ?? Date()
    }
    
    func changeFormat(newFormat: String?) -> String{
        let formatter = DateFormatter()
        formatter.dateStyle = .full
        formatter.timeZone = NSTimeZone.local
        formatter.dateFormat = newFormat
        let dateString = formatter.string(from: self)
        return dateString
    }
    
}

extension Bool{
    mutating func toggleVal() -> Bool{
        if self == true {
            self = false
            return self
        } else {
            self = true
            return self
        }
    }
}

extension Int {
    /// EZSE: Checks if the integer is even.
    public var isEven: Bool { return (self % 2 == 0) }
    
    /// EZSE: Checks if the integer is odd.
    public var isOdd: Bool { return (self % 2 != 0) }
    
    /// EZSE: Checks if the integer is positive.
    public var isPositive: Bool { return (self > 0) }
    
    /// EZSE: Checks if the integer is negative.
    public var isNegative: Bool { return (self < 0) }
    
    /// EZSE: Converts integer value to Double.
    public var toDouble: Double { return Double(self) }
    
    /// EZSE: Converts integer value to Float.
    public var toFloat: Float { return Float(self) }
    
    /// EZSE: Converts integer value to CGFloat.
    public var toCGFloat: CGFloat { return CGFloat(self) }
    
    /// EZSE: Converts integer value to String.
    public var toString: String { return String(self) }
    
    /// EZSE: Converts integer value to UInt.
    public var toUInt: UInt { return UInt(self) }
    
    /// EZSE: Converts integer value to Int32.
    public var toInt32: Int32 { return Int32(self) }
    
    /// EZSE: Converts integer value to a 0..<Int range. Useful in for loops.
    public var range: CountableRange<Int> { return 0..<self }
    
    /// EZSE: Returns number of digits in the integer.
    public var digits: Int {
        if self == 0 {
            return 1
        } else if Int(fabs(Double(self))) <= LONG_MAX {
            return Int(log10(fabs(Double(self)))) + 1
        }
    }
    
    
    
    /// EZSE: Returns a random integer number in the range min...max, inclusive.
    public static func random(within: Range<Int>) -> Int {
        let delta = within.upperBound - within.lowerBound
        return within.lowerBound + Int(arc4random_uniform(UInt32(delta)))
    }
}


extension String {
    
    /// EZSE: Converts String to Int
    public func toInt() -> Int? {
        if let num = NumberFormatter().number(from: self) {
            return num.intValue
        } else {
            return nil
        }
    }
    
    /// EZSE: Converts String to Double
    public func toDouble() -> Double? {
        if let num = NumberFormatter().number(from: self) {
            return num.doubleValue
        } else {
            return nil
        }
    }
    
    /// EZSE: Converts String to Float
    public func toFloat() -> Float? {
        if let num = NumberFormatter().number(from: self) {
            return num.floatValue
        } else {
            return nil
        }
    }
}

extension Date {
    
    func localToUTC(date:String) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "h:mm a"
        dateFormatter.calendar = NSCalendar.current
        dateFormatter.timeZone = TimeZone.current
        
        let dt = dateFormatter.date(from: date)
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        dateFormatter.dateFormat = "H:mm:ss"
        
        return dateFormatter.string(from: dt!)
    }
    
    func UTCToLocal(date:String) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "H:mm:ss"
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        
        let dt = dateFormatter.date(from: date)
        dateFormatter.timeZone = TimeZone.current
        dateFormatter.dateFormat = "h:mm a"
        
        return dateFormatter.string(from: dt!)
    }
    
    
    
    var millisecondsSince1970: String {
        
        return String((self.timeIntervalSince1970 * 1000.0).rounded())
    }
    
    
    
    
    init(milliseconds:Double) {
        self = Date(timeIntervalSince1970: TimeInterval(milliseconds / 1000))
    }
    
    func setTime(hour: Int, min: Int, sec: Int, timeZoneAbbrev: String = "UTC") -> Date? {
        let x: Set<Calendar.Component> = [.year, .month, .day, .hour, .minute, .second]
        let cal = Calendar.current
        var components = cal.dateComponents(x, from: self)
        
        components.timeZone = TimeZone(abbreviation: timeZoneAbbrev)
        components.hour = hour
        components.minute = min
        components.second = sec
        
        return cal.date(from: components)
    }
    
}

public func calculateTimeSince(time : String) -> String {
    let dateFormatter: DateFormatter = DateFormatter()
    //dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSZ"
    guard let locationDate: NSDate =  dateFormatter.date(from: time) as NSDate? else { return "" }
    print(locationDate)
    return  timeAgoSinceDate(date: locationDate,numericDates: true)
}

public func calculateTimeBK(time : String , format: String? = "") -> String {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
    let date = dateFormatter.date(from: time) ?? Date()
    let dateFormatter1 = DateFormatter()
    dateFormatter1.dateFormat = format == "" ? "MMM d yyyy. h:mm a" : format
    dateFormatter1.timeZone = TimeZone.current
    return dateFormatter1.string(from: date)
}

public func calculateTime(time : String , format: String? = "") -> String {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
    let date = dateFormatter.date(from: time) ?? Date()
    let dateFormatter1 = DateFormatter()
    dateFormatter1.dateFormat = format == "" ? "MMM d yyyy, h:mm a" : format
    dateFormatter1.timeZone = TimeZone(abbreviation: "UTC")
    return dateFormatter1.string(from: date)
}

public func timeAgoSinceDate(date:NSDate, numericDates:Bool) -> String {
    let calendar = NSCalendar.current
    let unitFlags: Set<Calendar.Component> = [.minute, .hour, .day, .weekOfYear, .month, .year, .second]
    let now = NSDate()
    let earliest = now.earlierDate(date as Date)
    let latest = (earliest == now as Date) ? date : now
    let components = calendar.dateComponents(unitFlags, from: earliest as Date,  to: latest as Date)
    
    if (components.year! >= 2) {
        return "\(components.year!)y ago"
    } else if (components.year! >= 1){
        if (numericDates){
            return "1y ago "
        } else {
            return "Last year"
        }
    } else if (components.month! >= 2) {
        return "\(components.month!)mo. ago"
    } else if (components.month! >= 1){
        if (numericDates){
            return "1mo. ago "
        } else {
            return "Last month"
        }
    } else if (components.weekOfYear! >= 2) {
        return "\(components.weekOfYear!)w ago"
    } else if (components.weekOfYear! >= 1){
        if (numericDates){
            return "1w ago "
        } else {
            return "Last week"
        }
    } else if (components.day! >= 2) {
        return "\(components.day!)d ago"
    } else if (components.day! >= 1){
        if (numericDates){
            return "1d ago "
        } else {
            return "Yesterday"
        }
    } else if (/components.hour >= 2) {
        return "\(components.hour!)h ago"
    } else if (/components.hour >= 1){
        if (numericDates){
            return "1h ago "
        } else {
            return "An hour "
        }
    } else if (/components.minute >= 2) {
        return "\(components.minute!)m ago"
    } else if (/components.minute >= 1){
        if (numericDates){
            return "1m ago "
        } else {
            return "A minute "
        }
    } else if (/components.second >= 45) {
        return "\(/components.second)s ago"
    } else {
        return "Just now"
    }
    
}




//extension CALayer  {
//    
//    func addShadow() {
//        self.shadowOffset = .zero
//        self.shadowOpacity = 1
//        self.shadowRadius = 16
//        self.shadowColor = UIColor.black.alpha(0.08).cgColor
//        self.masksToBounds = false
//        if cornerRadius != 0 {
//            addShadowWithRoundedCorners()
//        }
//    }
//    
//    func roundCorners(radius: CGFloat) {
//        self.cornerRadius = radius
//        if shadowOpacity != 0 {
//            addShadowWithRoundedCorners()
//        }
//    }
//    
//    private func addShadowWithRoundedCorners() {
//        if let contents = self.contents {
//            masksToBounds = false
//            sublayers?.filter{ $0.frame.equalTo(self.bounds) }
//                .forEach{ $0.roundCorners(radius: self.cornerRadius) }
//            self.contents = nil
//            if let sublayer = sublayers?.first,
//                sublayer.name == Constants.contentLayerName {
//                
//                sublayer.removeFromSuperlayer()
//            }
//            let contentLayer = CALayer()
//            contentLayer.name = Constants.contentLayerName
//            contentLayer.contents = contents
//            contentLayer.frame = bounds
//            contentLayer.cornerRadius = cornerRadius
//            contentLayer.masksToBounds = true
//            insertSublayer(contentLayer, at: 0)
//        }
//    }
//    
//}
extension Date{
    
    func presentableFormat(format :String? = nil, lineBreak :Bool? = nil) -> String {
        let ft = DateFormatter()
        if lineBreak != nil{
            ft.dateFormat = lineBreak! == true ? "dd/MM/yyyy\nh:mm a" : "dd/MM/yyyy h:mm a"
        }else{
            ft.dateFormat = format ?? "dd/MM/yyyy h:mm a"
        }
        return ft.string(from: self)
    }
    
}
