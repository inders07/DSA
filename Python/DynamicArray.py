
class DynamicArray:

    def __init__(self, capacity = 16):
        if capacity < 0:
            raise Exception("Illegal capacity : {0}".format(capacity))
        self.__len = 0      # current length of the array
        self.__capacity = capacity      # actual capacity of the array
        self.__arr = [None] * self.__capacity     # list to contain element
    
    def size(self):
        return self.__len
    
    def isEmpty(self):
        return self.__len == 0
    
    def get(self, index):
        if index < 0 or index >= self.__len:
            raise Exception("Index out of bounds")
        return self.__arr[index]
    
    def set(self, index, val):
        if index < 0 or index >= self.__len:
            raise Exception("Index out of bounds")
        self.__arr[index] = val
    
    def clear(self):
        for i in range(self.__len):
            self.__arr[i] = None
        self.__len = 0
    
    def add(self, val):
        # check if capacity is enough
        if self.__len + 1 >= self.__capacity:
            if self.__capacity == 0:
                self.__capacity = 1
            else:
                self.__capacity *= 2
            newArr = [None] * self.__capacity
            for i in range(self.__len):
                newArr[i] = self.__arr[i]
            self.arr = newArr
        
        self.__arr[self.__len] = val
        self.__len += 1

    def removeAt(self, index):
        if index < 0 or index >= self.__len:
            raise Exception("Index out of bounds")
        data = self.__arr[index]
        newArr = [None] * (self.__len - 1)
        for i in range(self.__len):
            if i < index:
                newArr[i] = self.__arr[i]
            elif i > index:
                newArr[i - 1] = self.__arr[i]
        return data
    
    def indexOf(self, val):
        for i in range(self.__len):
            if self.__arr[i] == val:
                return i
        return -1
    
    def contains(self, val):
        return self.indexOf(val) != -1
    
    def remove(self, val):
        index = self.indexOf(val)
        if index == -1:
            return False
        self.removeAt(index)
        return True
    
    def __str__(self):
        if self.__len == 0:
            return "[]"
        st = "["
        for i in range(self.__len - 1):
            st += str(self.__arr[i]) + ", "
        st += str(self.__arr[self.__len - 1]) + "]"
        
        return st

    def __iter__(self):
        self.__index = 0
        return self

    def __next__(self):
        if self.__index >= self.__len:
            raise StopIteration
        self.__index += 1
        return self.__arr[self.__index]


    