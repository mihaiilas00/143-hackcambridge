from collections import deque

a=deque([], maxlen=3)

class objects():
    def __init__(self, x):
        self.x=x

for i in range(10):
    
    a.append(i)
    
obj=objects(3)
a.append(obj)
print(a[2].x)
