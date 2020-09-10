An Android app called Twitterr.

Technologies Used

1.	AWS EC2 instance
2.	Parse Server
3.	Parse SDK Android
4.	Putty
5.	PuttyGen
6.	WinSCP

Functions

1.	Sign Up
2.	Log In
3.	Follow/Unfollow users
4.	Post a tweet
5.	See your feed
6.	See your tweet
7.	Logout

Details

This app is hosted on Parse Server after creating EC2 instance at AWS

Login screen appears as soon as you open the app. You need to sign up first. Sign Up button can be changed to Log In by clicking on the text written below it.

All the registered user appear once you login or open the app which are displayed using a listview. 
You can follow/unfollow any user by tapping on their name. A toast and green tick confirms that you have followed a particular user.

A privacy function is also added, you can see the tweets only from people you have followed.

When you click on the menu button on the top right corner a menu appears. You can post a tweet from there.(Used alert dialogue here).
•	You can see your feed, that is the tweets from people you have followed. 
•	You can also see your tweets under the button 'My Tweets'. 
•	The last option in the menu is of LogOut, and you come back to the LogIn screen.

Once a user posts a tweet it gets saved in the database along with the username, time of posting in the Tweet class.

The 'Feed' screen displays all the tweets from the people you have followed along with their usernames. This feed is also made using Listview with 2 things in each entry. The feed is shown in descending order of time of posting. Most recent on the top. It gets updated as soon as you follow/unfollow someone.

The 'My Tweets' screen displays all the tweets from the user. Listview is used here also. Again all the tweets are in descending order of the time of posting.

The last option is to logOut.

