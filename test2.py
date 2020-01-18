x=3

def fcn():
    global x
    x=12

fcn()
print(x)

y = "Mihai is a nerd"

def lies():
    global y
    y = "Mihai is not a nerd"

lies()
print(y)
