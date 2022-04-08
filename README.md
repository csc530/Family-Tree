# Family-Tree

The continuous and final assignment for (2022W) COMP-3025G-WAB - Mobile and Pervasive Computing. A family tree app for android.

## When marking

If you installed the app from the play store, you may be unable to login with google but email works fine; if you installed the app from the github, you should be able to login with google.

Please ignore all the fluff in the console about 
```txt
W/Firestore: (24.1.0) [CustomClassMapper]: No setter/field for age found on class com.csc530.familytree.models.FamilyMember (fields/setters are case sensitive!)
W/Firestore: (24.1.0) [CustomClassMapper]: No setter/field for birthday found on class com.csc530.familytree.models.FamilyMember (fields/setters are case sensitive!)aidl
```
firestore is trying to set the fields of functions which don't have any

## About

A family tree app to diagram the relationships of a family from parents to children

![A family tree with 2 parents and 3 children](/../assignment-tasks/docs/img/example@1x.png)

## More information

Please visit [my GitHub page](https://csc530.github.io/Family-Tree/) for more details regarding my app and it's development😇

Or you may visit the assignment details branch (`assignment-tasks`) [README](/../../assignment-tasks/README.md) in the for more information on required and met criteria on COMP3025 final (and continuous) project

## Credits

Big thanks to these for these resource providing their services, images, etc. for my app

- [SVG Repo](https://www.svgrepo.com/svg/157858/plus): icons
- ~~[Mattia Iavarone &  Markus Ressel](https://github.com/natario1/ZoomLayout): zoom functionality on family trees~~
- ~~[Block & Block](https://github.com/oss-bandb/GraphView): family tree structure and layouts~~
- [Balkan Family TreeJS](https://balkan.app/FamilyTreeJS) Family tree view
- [Termly io](https://termly.io) for my [privacy policy](/../../assignment-tasks/docs/privacy-policy.md)
- [Picasso](https://square.github.io/picasso/) for image loading