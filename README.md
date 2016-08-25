# SetByLinkedList
Implement a set class using doubly linked list, union O(n1+n2), Union sets O(nlgn)

Sets are a group of elements without duplicates

To union two sets in Time O(n1+n2), space O(1) except for memory of inputs and outputs, the elements in linked list have to be in order

To union n sets in O(nlgn), we should use binary union based on O(n1+n2) union.

We could also implement binary union in non-recursive way, by loops
