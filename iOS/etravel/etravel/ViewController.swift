//
//  ViewController.swift
//  etravel
//
//  Created by Fabian Okeke on 6/29/15.
//  Copyright (c) 2015 IBM. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var emailField: UITextField!
    
    @IBAction func submitButton(sender: AnyObject) {
        
        if emailField.text != "" {
            self.performSegueWithIdentifier("profileSegue", sender: self)
        }
        else {
            let alert = UIAlertView()
            alert.message = "Email field can't be blank."
            alert.addButtonWithTitle("Okay, I'll Update")
            alert.show()

        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject!) {
        if (segue.identifier == "profileSegue") {
            var profileVC = segue.destinationViewController as! ProfileViewController
            profileVC.emailPassed = self.emailField.text
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    
}


