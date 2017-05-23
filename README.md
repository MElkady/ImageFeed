# ImageFeed

ImageFeed is a showcase native Android app to search for images on social media. At the moment, ImageFeed supports Twitter & Instagram, more channels will come later.

ImageFeed follows Model-View-Presenter (MVP) architecture & trying to apply SOLID principles. Packages classes/interfaces by function as following:

| Package | Function |
| ------ | ------ |
| me.elkady.imagefeed.search | Contains contract (view interface & presenter interface) for search screen along with actual implementation of the presenter, view (fragment) & the containing activity |
| me.elkady.imagefeed.history | Contains contract (view interface & presenter interface) for history screen along with actual implementation of the presenter, view (fragment) & the containing activity |
| me.elkady.imagefeed.models | POJO models |
| me.elkady.imagefeed.data | Contains data respositories |
| me.elkady.imagefeed.data.db | Contains DB-related classes |
| me.elkady.imagefeed.data.network | Contains network-related classes |

![](http://www.goxuni.com/wp-content/uploads/2016/05/MVP1.png)



# Next steps!

- Replacing callbacks with RxJava to make code more readable.
- Adding more channels


