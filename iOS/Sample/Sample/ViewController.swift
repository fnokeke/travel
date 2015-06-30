//
//  ViewController.swift
//  Sample
//
//  Created by Fabian Okeke on 5/21/15.
//  Copyright (c) 2015 IBM. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var menuSelected: UILabel!

    @IBAction func buttonPressed(sender: UIButton) {
        //handle button     s
        let title = sender.titleForState(.Normal)!
        menuSelected.text = "\(title) it is :)"
    }

}

