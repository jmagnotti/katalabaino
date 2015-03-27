# Introduction #

ItemSplitter is a routine that is used to determine how a subject performs on novel items, familiar items, or a mix of the two.

# Usage #

Used in conjunction with SetSizeConstants, the ItemSplitter can parse apart performance based on a chosen vector of items.  Once imported to the appropriate analysis class, the ItemSplitter compares all sample and probe items to the created vector.  In most designs, this will create up to four different performance classes.

```
import splitters.ItemSplitter;
...

{ // begin class

    public static void main(String args[])
    {
        ...

        analysis.addSplitter(new ItemSplitter(SetSizeConstants.GetInstance().mts6itemSet)));

        ...
    }

} // end class
```

# Example of use #
Subjects training in a same/different task are currently training in a 32-item set.  If we would like to see how well they are performing on the previously learned 8-item set, we can reference that set from its SetSizeConstants vector (in this case, "sd8itemSet").  If we decided to look at 16-item performance, the 16-item vector includes the 8-item set as well, so there would be no need to run both.

## Performance Classes ##

**F-F:**  Familiar-Familiar trials.  This is calculated for all trials in which both the sample and probe items are contained within the appropriate ItemSplitter vector.

**F-N:**  Familiar-Novel trials.  This is calculated for all trials in which the sample is familiar (contained within the vector) and the probe items are not (suggesting they are untrained).

**N-F:**  Novel-Familiar trials.  This is calculated for all trials in which the sample is novel (_not_ contained within the vector) and the probe items are familiar.

**N-N:**  Novel-Novel trials.  This is calculated for all trials in which both the sample and probe items are not contained within the vector.  This would suggest that all components of the trial were untrained or novel.