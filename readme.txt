Here’s a standard micro service approach to a LRU cache if you were to implement this type of cache without
using a distributed data grid such as Hazelcast.

It contains an example H2 persistent store and converts complex object types to JSON.

Used as a singleton bean, there needs to be care as to which type of objects are put and retrieved
so as not to run into serialisation issues.
Used via instantiation there is no concern here.

Limited time, so only tested the classes I wrote and not Java's LinkedHashMap implementation
for edge cases around removing the eldest entry.