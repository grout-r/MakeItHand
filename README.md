# Make It Hand

Android application who will convert user's string into a hand written image thanks to the Handwriting.io API.

Be careful of special characteres like "é", "à", "ç" [...], they are supported by Handwritting.io

## Tech stack
  Default Android stack. No added libraries.
  IDE : Android Studio.
  Android SDK minimum : v25

## Data Structure : Writing

  The Writing object is the model of the app. It contains a title, a value (the user's string) and a path (the image).
  It can be serialized throught intents.
  
## Data persistance : SQLite

  An SQLite database is set up in order to keep the generated writing from one use to another.
  
## Network calls

  The net calls to the Handwriting.io API are made asynchronous is order to not freeze the UI.
  While processing requests, a loading bar is displayed on the Main Activity.
  
## Activity :  Main Activity

  You will find in the Main Activity a ListView with all the kept writings. The Writing object is displayed thanks to a custom array adapter.
  
## Activity : New Writing

  Form to create a new writing.
  
## Activity : Show Writing

  Triggered by clicking an item on the Main Activity List View, will show you the image and the original text.
  
## Javadoc

  You will find all the code documentation directly into the java files, as a JavaDoc.
