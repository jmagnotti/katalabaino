# Introduction #

SetSizeConstants is a class that works to inform the program of what items are included in their respective training set sizes.  This class currently contains vectors for both matching-to-sample training sets and same/different training sets.

# Usage #

Training set size is manipulated in several of our studies, and during normal data analysis, it is important to determine what items are from the current training set and which items come from previous training sets. SetSizeConstants, when used with ItemSplitter, informs performance based on these criteria.

When implementing this in an analysis, the vector within SetSizeConstants can be used directly.  For example:

```
analysis.addSplitter(new ItemSplitter(SetSizeConstants.GetInstance().mts6itemSet));
```

# Adding new image sets #
To add new image sets, declare a public vector in the SetSizeConstants class and then initialize within the private SetSizeConstants() constructor.

```
public SetSizeConstants
{
    ...

    public Vector<String> myNewImageSet;

    ...

    public SetSizeContants() 
    {
        ...

        myNewImageSet = new Vector<String>();
        myNewImageSet.add("image001.bmp");
        myNewImageSet.add("image002.bmp");

        ...
    }

    ...
} // end class
```

# Implementation note #
The SetSizeConstants class use the Singleton Pattern to avoid duplicate creation of the image vectors. The vectors are loaded at first call to the accessor method.