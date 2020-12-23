
class DoublyLinkedList:
    
    def __init__(self):
        self.__size = 0
        self.__head = None
        self.__tail = None

    class _Node:
        def __init__(self, val, prev, next):
            self.val = val
            self.prev = prev
            self.next = next
        
        def __str__(self):
            return str(self.val)
    
    # clear the list
    def clear(self):
        trav = self.__head
        while trav != None:
            next = trav.next
            trav.data = None
            trav.prev = None
            trav.next = None
            trav = next
        self.__head = self.__tail = trav = None
        self.__size = 0
    