# JsonPlaceholder Api (Albums)
JsonPlaceholder API challenge using the albums api

This app can be further improved by: 

Adding pagination to the recyclerview to only load small amout of data at a time to improve performance when data becomes too many. At the moment there isn't much performance issue since there is only 100 records

Adding a broadcast receiver that check for internet connectivity. At the moment i am only showing a simple message when there is no internet connection and the app fails to fetch data from the api.

At the moment the user has to close and open the app again when they connect to the internet. That can be improve by prompting the app to retry fetching the data when internet connection become available

