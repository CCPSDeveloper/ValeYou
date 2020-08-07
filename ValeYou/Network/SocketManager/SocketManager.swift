//  MEX
//
//  Created by Pankaj Sharma on 03/04/20.
//  Copyright © 2020 Pankaj Sharma. All rights reserved.
//


import Foundation
import SocketIO

protocol EventListener:NSObjectProtocol{
    func didReceiveMessage(message:[String:Any])
    func didJoinInbox(data:[String:Any])
    func didStartTyping(data:[String:Any])
    func didStopTyping(data:[String:Any])
    func didJoinChat(data:[String:Any])
    func didUnjoinChat(data:[String:Any])
    func didReceiveAllMessages(data:[String:Any])
    func didConnected()
}

class SocketHandler{
    
    let manager = SocketManager(socketURL: URL(string:"http://3.17.254.50:4100")!, config:[.log(true), .forcePolling(true),.forceNew(true)])
    let socket:SocketIOClient?

    var paramString = [String:Any]()
    var queryString = String()
    var delegate:EventListener!
    
    init(listenerDelegate:EventListener){
        socket = manager.defaultSocket
        delegate = listenerDelegate
        self.setSocketEvents()
    }
    
    private func setSocketEvents(){
        guard let socket = self.socket else { return }
        
        socket.once(clientEvent: .connect) { (data, ack) in
            print("socket connected!")
            socket.emit(self.queryString,self.paramString)
        }
        
        socket.on(clientEvent: .disconnect) { (data, ack) in
            print("socket Disconnected")
        }
        
        socket.on("online") { (data, ack) in
            print(data,ack)
            let dict = data.first as! [String:Any]
            self.delegate.didJoinChat(data:dict)
        }
        
        socket.on("connect_listener"){(data,ack) in
            print(data,ack)
            self.delegate.didConnected()
            
        }
        
        socket.on("offline") { (data, ack) in
            print(data,ack)
            let dict = data.first as! [String:Any]
            let userId = (dict as! NSDictionary).value(forKey: "user_id") as! String
            
            self.delegate.didUnjoinChat(data: dict)
        }
        
        socket.on("new_message") { (data, ack) in
            let dict = data.first as! [String:Any]
            print(dict)
            self.delegate.didReceiveMessage(message: dict)
            print(data,ack)
        }
        
        socket.on("get_data_message"){ (data,ack) in
            let dict = data.first as! [String:Any]
            print(dict)
            self.delegate.didReceiveAllMessages(data:dict)
        }
        
        socket.on("typing") { (data, ack) in
            print(data,ack)
            print("started typing ")
             let userId = data.first as! String
            self.delegate.didStartTyping(data: ["userId":userId])
        }
        
        socket.on("stop_typing") { (data, ack) in
            print(data,ack)
            print("started typing ")
            let userId = data.first as! String
            self.delegate.didStopTyping(data: ["userId":userId])
        }
    }
    
    func connectUser(params:[String:Any]){
       // let parameters = self.convertDictToJsonString(params: params)
//        guard let parameter = parameters else {
//            print("nil found")
//            return
//        }
        self.queryString = "connect_user"
        self.paramString = params
        self.socket?.connect()
    }
    
    func join(params:[String:String]){
//        let parameters = self.convertDictToJsonString(params: params)
//        guard let parameter = parameters else {
//            print("nil found")
//            return
//        }
        self.queryString = "join"
        self.paramString = params
        self.socket?.connect()
       // self.socket.emit("join")
    }
    
    func getAllMessages(params:[String:Any]){
        let parameters = self.convertDictToJsonString(params: params)
        guard let param = parameters else { return }
        self.socket?.emit("get_message", params)
    }
    

    func sendMessage(params:[String:Any]){
        let parameters = self.convertDictToJsonString(params: params)
        guard let parameter = parameters else{
            print("nil found")
            return
        }
        self.socket?.emit("send_message", params)
    }
    
    func startTyping(params:[String:String]){
        let parameters = self.convertDictToJsonString(params: params)
        guard let parameter = parameters else{
            print("nil found")
            return
        }
        self.socket?.emit("typing", params)
    }
    
    func stopTyping(params:[String:String]){
        let parameters = self.convertDictToJsonString(params: params)
        guard let parameter = parameters else{
            print("nil found")
            return
        }
        self.socket?.emit("stoptyping", params)
    }
    
    func disconnect(params:[String:String]){
        let parameters = self.convertDictToJsonString(params: params)
        guard let parameter = parameters else{
            print("nil found")
            return
        }
        self.socket?.emit("unjoin",params)
    }
    
    func socketDisconnect(){
        self.socket?.disconnect()
    }
    
    func convertDictToJsonString(params:[String:Any])->String?{
        do{
            let jsonData = try JSONSerialization.data(withJSONObject: params, options: .init(rawValue: 0))
            let jsonString = String(data: jsonData, encoding: .ascii)
            print(jsonString)
            return jsonString
        }catch{
            print("error while convert dict to json")
        }
        return nil
    }
   
}
