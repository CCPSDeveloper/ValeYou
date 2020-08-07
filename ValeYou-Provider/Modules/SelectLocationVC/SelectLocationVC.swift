//
//  SelectLocationVC.swift
//  FollowMe
//
//  Created by Pankaj Sharma on 01/03/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import UIKit
import GoogleMaps

protocol SelectLocationDelegate{
    func locationSelected(address:String,city:String,state:String,country:String,pincode:String,coordinates:CLLocationCoordinate2D)
}

class SelectLocationVC: UIViewController,GMSMapViewDelegate {
    
    @IBOutlet weak var btnDone: UIButton!
    @IBOutlet weak var lblAddress: UILabel!
    @IBOutlet weak var mapView: GMSMapView!
    var locationManager:CLLocationManager!
    var delegate:SelectLocationDelegate!
    var coordinates:CLLocationCoordinate2D!
    
    var address = ""
    var state = ""
    var city = ""
    var country = ""
    var pincode = ""

    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.delegate = self
        if L102Language.isRTL{
            btnDone.setTitle("Done".localize, for: .normal)
            //lblAddress.text = "اختر موقعا"
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()
        
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        locationManager.stopUpdatingLocation()
    }


    @IBAction func btnActionBack(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func btnActionDone(_ sender: Any) {
        if lblAddress.text != "Select Location"{
            self.delegate.locationSelected(address: lblAddress.text!, city: city, state: state, country: country, pincode: pincode, coordinates: coordinates)
            self.dismiss(animated: true, completion: nil)
        }
    }
}

extension SelectLocationVC:CLLocationManagerDelegate{
    
    func getAddressFromLatLon(pdblLatitude: Double, withLongitude pdblLongitude: Double) {
                var center : CLLocationCoordinate2D = CLLocationCoordinate2D()
                let lat: Double = Double("\(pdblLatitude)")!
                
                let lon: Double = Double("\(pdblLongitude)")!
                
                let ceo: CLGeocoder = CLGeocoder()
                center.latitude = lat
                center.longitude = lon
                
                let loc: CLLocation = CLLocation(latitude:center.latitude, longitude: center.longitude)
                
                ceo.reverseGeocodeLocation(loc, completionHandler:
                    {(placemarks, error) in
                        DispatchQueue.main.async {
                            
                            if (error != nil)
                            {
                                print("reverse geodcode fail: \(error!.localizedDescription)")
                            }
                            let pm = placemarks! as [CLPlacemark]
                            
                            if pm.count > 0 {
                                let pm = placemarks![0]
                                
                                var addressString : String = ""
                                if pm.subLocality != nil {
                                      addressString = addressString + pm.subLocality! + ", "
                                   
                                    // self.cityTF.text = pm.
                                }
                                if pm.thoroughfare != nil {
                                    
                                    addressString = addressString + pm.thoroughfare! + ", "
                                }
                                
                                if pm.locality != nil {
                                     self.city = pm.locality ?? ""
                                    addressString = addressString + pm.locality! + ", "
                                }
                                if pm.administrativeArea != nil{
                                    self.state = pm.administrativeArea ?? ""
                                    addressString = addressString + (pm.administrativeArea ?? "") + ", "
                                }
                                if pm.country != nil {
                                    self.country = pm.country ?? ""
                                       addressString = addressString + pm.country! + " "
                                }
                                if pm.postalCode != nil {
                                    
                                    self.pincode = pm.postalCode ?? ""
                                    // addressString = addressString + pm.postalCode! + " "
                                    
                                }
                                let address = addressString
                                self.lblAddress.text = address
                                
                            }
                        }
                        
                })
                
            }
    
    func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition) {
 
        let coordinate = mapView.camera.target
        self.coordinates = coordinate
        getAddressFromLatLon(pdblLatitude: coordinate.latitude, withLongitude: coordinate.longitude)
        
    }
    
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
                  guard let location = locations.last else {return}
                  let latitude = location.coordinate.latitude
                  let longitude = location.coordinate.longitude
                  setMap(lat:latitude,long:longitude)
                  getAddressFromLatLon(pdblLatitude: latitude, withLongitude: longitude)
                  locationManager.stopUpdatingLocation()
              }
              
              func setMap(lat:Double,long:Double){
                  let camera = GMSCameraPosition(latitude: lat, longitude: long, zoom: 16)
                  mapView.animate(to: camera)
              }

    }
    
    
   
