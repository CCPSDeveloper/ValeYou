//
//  HomeVC + Map.swift
//  ValeYou
//
//  Created by Techwin on 06/08/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//

import Foundation
import GoogleMaps
import CoreLocation

extension HomeVC: GMSMapViewDelegate{
    

    
//    func loadMapOnView(){
//        DispatchQueue.main.async {
//            let camera = GMSCameraPosition.camera(withLatitude:0.0, longitude: 0.0, zoom: 1.0)
//            let mapFrame = UIView(frame: CGRect(x:0, y:self.mapViewBase.frame.origin.y, width:UIScreen.main.bounds.width, height:self.mapViewBase.frame.size.height))
//            self.gmsMapView = GMSMapView.map(withFrame: mapFrame.bounds, camera: camera)
//            //            self.gmsMapView.mapStyle(withFilename: "customMapStyle", andType: "json")
//            self.gmsMapView.delegate = self
//            self.mapViewBase.addSubview(self.gmsMapView)
//
//            self.view.layoutIfNeeded()
//
//            //HANDLE LOCATION
//            CoreLocationManager.shared.getCurrentLocation(self) { (location) in
//                if let loc = location{
//                    //                        let currentLocation = CLLocation(latitude: loc.latitude, longitude: loc.longitude)
//
//                    for place in self.places {
//                        print(place)
//                        let marker = GMSMarker()
//                        let markerView = JobMarkerView(frame: CGRect(x: 0, y: 0, width: 50, height: 50))
//                        markerView.base.roundedCorners(25)
//                        marker.position = CLLocationCoordinate2DMake(place.lat, place.long)
//                        //                marker.icon = GMSMarker.markerImage(with: .black)
//                        if let loc = self.currentLocation{
//                            //   marker.rotation = loc.latitude
//                        }
//                        marker.userData = place.orders  //TODO:- WILL BE CHANGED TO JSON ON API INTEGRATION
//                        if place.orders.count > 1{
//                            markerView.distance.text = "\(place.orders.count)"
//
//                        }else{
//                            markerView.distance.text = "5 Km"
//
//                        }
//                        marker.iconView = markerView.contentView
//                        marker.title = place.name
//                        marker.snippet = "Hey, this is \(place.name)"
//                        marker.map = self.gmsMapView
//                    }
//
//                    self.currentLocation = loc
//                    self.gmsMapView.animate(toLocation: loc)
//                    self.gmsMapView.animate(toZoom: 15)
//                }
//            }
//
//
//        }
//    }
    
    func addMarkersOnMap(users: [GetProviderListData]){
        self.mapView.clear()
        let bounds = GMSCoordinateBounds()

        for place in users {
            print(place)
            let marker = GMSMarker()
            let markerView = JobMarkerView(frame: CGRect(x: 0, y: 0, width: 50, height: 50))
            markerView.base.cornerRadius = 25
            markerView.base.backgroundColor = .red
            marker.position = CLLocationCoordinate2DMake(place.latitude, place.longitude)
            //                marker.icon = GMSMarker.markerImage(with: .black)
//            if let loc = self.currentLocation{
//                //   marker.rotation = loc.latitude
//            }
            marker.userData = place  //TODO:- WILL BE CHANGED TO JSON ON API INTEGRATION
//            if place.orders.count > 1{
//                markerView.distance.text = "\(place.orders.count)"
//
//            }else{
//                markerView.distance.text = ""
//
//            }
        
            marker.iconView = markerView.contentView
            marker.title = place.firstName + " " + place.lastName
            marker.snippet = place.address
            marker.map = self.mapView
            bounds.includingCoordinate(marker.position)
        }
        
//        let bounds = mapView.reduce(GMSCoordinateBounds()) {
//            $0.includingCoordinate($1.position)
//        }
        DispatchQueue.main.async{
            print(bounds.debugDescription)
            self.mapView.animate(with: .fit(bounds, withPadding: 25.0))
        }
        }
 
    
//    MARK:- GMSMapViewDelegate
    
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        
//        if !orderInfoSheetLoaded{
//            addBottomSheetView(marker: marker)
//        }else{
//            NotificationCenter.default.post(name: .jobMarkerSwitched, object: marker)
//        }
        return true
    }
 
}
