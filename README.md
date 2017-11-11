# NOTICE
This app is in BETA and in progress and while it works, enhancements are planned.

# TL;DR
Run this app to see how many times the Connectivity Action is called and what data was passed.

# connectivity-broadcast-counter
Do you use the CONNECTIVITY_ACTION broadcast receiver in your app? When is the action called and under what conditions? The documentation is fairly light in the google developer docs. Also, this event may be firing much more than you expect.

This app captures the data from the CONNECTIVITY_ACTION broadcasts and displays them in a recycler view to the user.

# Planned enhancements
* Animation on recycler view being updated
* Animation on recycler item being added
* Selectable old/new style of broadcast receiver (in manifest for older devices < 7.0, or programmatically for any version)

# Completed enhancements
* ~~Grouping and counting by date~~
* ~~Dive into the details of the event~~
* ~~Remove all pokemon from the app~~
* ~~Refresh via Room Rx extensions when data changes~~


