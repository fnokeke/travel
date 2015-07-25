//
//  ProfileViewController.swift
//  etravel
//
//  Created by Fabian Okeke on 7/2/15.
//  Copyright (c) 2015 IBM. All rights reserved.
//

import UIKit

class ProfileViewController: UIViewController {
    
    var emailPassed:String!
    var result:String!
    
    @IBOutlet weak var firstnameField: UITextField!
    @IBOutlet weak var middlenameField: UITextField!
    @IBOutlet weak var lastnameField: UITextField!
    @IBOutlet weak var dateofbirthField: UITextField!
    @IBOutlet weak var emailField: UITextField!

    
    @IBAction func populateButton(sender: AnyObject) {
        populateFields(self.emailField.text)
    }
    
    @IBAction func saveButton(sender: AnyObject) {
    }
    
    @IBAction func resetButton(sender: AnyObject) {
        self.firstnameField.text = ""
        self.middlenameField.text = ""
        self.lastnameField.text = ""
        self.dateofbirthField.text = ""
        self.emailField.text = self.emailField.text
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        populateFields(emailPassed)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
     func populateFields(email: String) {
        var result = ""
        
        var query: String = "SELECT * from PROFILE.employee where email='\(email)'"
        println("The query is: " + query)
        query = query.stringByReplacingOccurrencesOfString(" ", withString: "%20")
        
        var jsonRequest: String = "http://192.168.56.101:9080/PhoneToWeb/v1/request/query/" + query
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
                    if var postTitle = post["1"] as? String
                    {
                      
                        result = postTitle
                        
                        println(String(format: "The result is: " + postTitle))
                        
                        var tmpArr = split(result) {$0 == ","}
                        println(String(format: "firstname: " + tmpArr[0]))
                        println(String(format: "lastname: " + tmpArr[1]))
                        println(String(format: "email: " + tmpArr[2]))
                        
                        // populate fields
                        dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        self.firstnameField.text = tmpArr[0]
                        self.lastnameField.text = tmpArr[1]
                        self.emailField.text = tmpArr[2]
                        })

                    }
                }
            }
        })
        
    }
    
    //
    // JSON functions
    //
    func getJSON(urlToRequest: String) -> NSData{
        return NSData(contentsOfURL: NSURL(string: urlToRequest)!)!
    }
    
    func parseJSON(inputData: NSData) -> NSDictionary{
        var error: NSError?
        var boardsDictionary: NSDictionary = NSJSONSerialization.JSONObjectWithData(inputData, options: NSJSONReadingOptions.MutableContainers, error: &error)  as! NSDictionary
        
        return boardsDictionary
    }
    
    
  }
