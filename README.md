# DialogPro
Dialog contains multiple fragments which can be explored by swiping.

![Example](https://stevelukis.com/cloud/dialogpro/example.gif)

### Flag
There are two flags available.
- FLAG_KEEP
This is the default flag. The fragments will be kept in memory after being swiped through. Suitable if you only have a few fragments.
- FLAG_DESTROY_IMMEDIATELY
The fragments will be removed from memory after being swiped through. Suitable if you have a lot of fragments.

### Usage

#### Kotlin
```
DialogPro()
        .setFlag(DialogPro.FLAG_KEEP) // setting the flag
        .setFragments(fragments) // passing an array of Fragment
        .setOnPageSelectedListener(object : OnPageSelectedListener {
             override fun onPageSelected(pagePosition: Int) {
                   // logic
             }
        }) // implementing the listener
        .show(this) //showing the dialog
```

#### Java
````
new DialogPro()
        .setFlag(DialogPro.FLAG_KEEP) // setting the flag
        .setFragments(fragments) // passing an array of Fragment
        .setOnPageSelectedListener(new OnPageSelectedListener() {
             override fun onPageSelected(int pagePosition) {
                   // logic
             }
        }) // implementing the listener
        .show(this); //showing the dialog
````
