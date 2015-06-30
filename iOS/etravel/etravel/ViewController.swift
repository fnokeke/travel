//
//  ViewController.swift
//  etravel
//
//  Created by Fabian Okeke on 6/29/15.
//  Copyright (c) 2015 IBM. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    var result = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        var query: String = "INSERT INTO table employee VALUES ('ooboi', 'ackerman', 15, 220930)"
        query = query.stringByReplacingOccurrencesOfString(" ", withString: "%20")
        
        var jsonRequest: String = "http://192.168.56.101:9080/PhoneToWeb/api/request/query/" + query
        var urlRequest = NSURLRequest(URL: NSURL(string: jsonRequest)!)
        
        //call
        NSURLConnection.sendAsynchronousRequest(urlRequest, queue: NSOperationQueue(), completionHandler:{_ -> Void in})
        
        //handle response
        NSURLConnection.sendAsynchronousRequest(urlRequest, queue: NSOperationQueue(), completionHandler:{
            (response:NSURLResponse!, data: NSData!, error: NSError!) -> Void in
            if let anError = error
            {
                // got an error in getting the data, need to handle it
                println("error calling GET webserver query")
                println(error.localizedDescription)
            }
            else // no error returned by URL request
            {
                // parse the result as json, since that's what the API provides
                var jsonError: NSError?
                let post = NSJSONSerialization.JSONObjectWithData(data, options: nil, error: &jsonError) as! NSDictionary
                if let aJSONError = jsonError
                {
                    // got an error while parsing the data, need to handle it
                    println("error parsing /posts/1")
                }
                else
                {
                    // now we have the post, let's just print it to prove we can access it
                    println("Result is:\n" + post.description)
                    
                    // the post object is a dictionary
                    // so we just access the title using the "title" key
                    // so check for a title and print it if we have one
                    if var postTitle = post["query"] as? String
                    {
                        println("The result is: " + postTitle)
                        self.result = postTitle
                    }
                }
            }
        })


    }
    
    
    // helper functions
    func getJSON(urlToRequest: String) -> NSData{
        return NSData(contentsOfURL: NSURL(string: urlToRequest)!)!
    }
    
    func parseJSON(inputData: NSData) -> NSDictionary{
        var error: NSError?
        var boardsDictionary: NSDictionary = NSJSONSerialization.JSONObjectWithData(inputData, options: NSJSONReadingOptions.MutableContainers, error: &error) as! NSDictionary
        
        return boardsDictionary
    }
    
    
    // UI view
    @IBOutlet weak var ourLabel: UILabel!
    
    @IBAction func buttonPressed(sender: AnyObject) {
        ourLabel.text = self.result
        
    }
    
    @IBAction func resetButton(sender: AnyObject) {
        ourLabel.text = "Reset message here"
        
    }
    
    
    
    
    
}


