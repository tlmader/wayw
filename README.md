# wayw
An Android application and platform for sharing outfits.

Wayw provides a mobile service for style sharing.
It is tailored for users who are interested in browsing or sharing outfits.
Items featured in an image can be tagged with added details and descriptions.
There will be pre-defined types of details, and users will include the information.
Genres allow grouping of similar styles. There will be pre-defined and user-created genres.
Users can follow other users as well as genres.

Database
  User
    Username
    Email
    Password
    Name
    Gender
    Location
    AboutMe
    Following[]
      User[]
      Genre[]
  Fit
    Genre
    User
    Name
    Description
  Item[]
    Fit
    Tag
    Detail[]
      Type
      Description
  Genre
    Name
    Description

Adding a Fit
1. Upload image
2. Name and description
3. Tag to add an item
4. Add details to item

# Tutorials

Android Facebook like Custom ListView Feed using Volley
http://www.androidhive.info/2014/06/android-facebook-like-custom-listview-feed-using-volley/
