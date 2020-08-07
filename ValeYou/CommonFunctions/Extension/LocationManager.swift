//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import CoreLocation
 
class LocationManager: NSObject, CLLocationManagerDelegate {
    static let sharedInstance: LocationManager = {
        let instance = LocationManager()
        // setup code
        return instance
    }()
    
   var locationManager:CLLocationManager!
    
    func determineMyCurrentLocation() {
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            locationManager.startUpdatingLocation()
            //locationManager.startUpdatingHeading()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let userLocation:CLLocation = locations[0] as CLLocation
        debugPrint("user latitude = \(userLocation.coordinate.latitude)")
        debugPrint("user longitude = \(userLocation.coordinate.longitude)")
//        objLocationData.currentLat = userLocation.coordinate.latitude
//        objLocationData.currentLong = userLocation.coordinate.longitude
        
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error)
    {
        print("Error \(error)")
    }
}
