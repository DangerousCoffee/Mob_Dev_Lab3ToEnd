### Assignment 3  
-Reading files in application context.  
-Tried to implement bottom navigation bar hiding on scroll up but it is done using Coordinator Layout.  
IDE cannot find the Coordinator Layout package(even though i tried adding all the necessary dependencies).  
Currently the table is padded at the bottom to a bit over bottom navigation bar height.  
-When using landscape the layout doesn't fill the view to width. Either this is unnatural behaiviour or i have to implementnt layouts for landscape and portrait modes.  
Currently when using "match parent" option android will position view on the left and leave empty space to the right.   
When using "wrap content" android will position the view in the middle.  
In both cases there's not enough content to fill landscape mode and android doesn't stretch out views, hence the weird behaviour in landscape mode.    


### Assignment 4  
-When deleting items you need to notify the table adapter that the dataset changed. However if you do it normally it deletes the item without animation making it confusing.  
To have animation i had to delete the item and notify the adapter that a specific item was removed and the subset of items from the deleted item onwards has changed.  
-To implement table search i had to use SearchView inflated in menu part of the app. To actually filter the data official tutorials advise to implement a simple database with SQLite helper framework. Then you write the data into your database and get it with a regular SQLite query. This returns a Cursor object that you'd have to use to populate the new table. But i was unable to find any tutorials on how to use Cursor objects to populate tables or filter out table entries.  
Second option is to use table adapter as a database and have it filter out the search request. For that i had to use SearchView listeners to get the query in String form and use it to filter out the data.  
-To create detailed view of the card i used another activity that i had to send the card object to. Since android doesn't allow to exchange objects between activities normally, i had to implement card class as Parceable.    


### Assignment 5
-Normal android RecyclerView does not allow some features of CollectionView. It is possible to implement a custom layout manager to create a recycled list with a structure similar to task. However right now it is implemented as a non-recyclable linear series of layout prefabs. As such the app will show noticeable lag when number of images starts getting high enough(50-100 images). It is possible to use staggered layout manager or flexbox library to implement a RecyclerView where each image can have custom width and height. However this solution does not allow to arrange multiple images in a row inside a single column.

### Assignment 6
-For graph an external library was used since normal libraries don't support drawing graphs via data sets.
